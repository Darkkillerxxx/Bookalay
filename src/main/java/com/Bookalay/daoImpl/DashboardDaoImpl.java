package com.Bookalay.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Bookalay.dao.DashboardDao;
import com.Bookalay.pojo.ParentUser;
import com.Bookalay.pojo.Request;
import com.Bookalay.util.DateUtil;
import com.Bookalay.util.DbUtil;

/**
 * DashboardImpl
 *
 * Provides DB methods used by the Parent dashboard:
 * - aggregate metrics (counts)
 * - recent requests
 * - issued books
 * - overdue books
 * - returned history
 * - upcoming due (next N days)
 *
 * Note: This class uses Request and Book POJOs and fills only fields required by dashboard views.
 */
public class DashboardDaoImpl implements DashboardDao {

    /**
     * Count helper for single-value queries
     */
    private int countQuery(String sql, Object... params) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /* -------------------------
       Aggregate metric methods
       ------------------------- */
    @Override
    public int getTotalRequests(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ?";
        return countQuery(sql, parentId);
    }
    
    @Override
    public int getTotalApproved(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'approved'";
        return countQuery(sql, parentId);
    }
    
    @Override
    public int getBooksCurrentlyIssued(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'issued'";
        return countQuery(sql, parentId);
    }
    
    @Override
    public int getBooksReturned(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'returned'";
        return countQuery(sql, parentId);
    }
    
    @Override
    public int getPendingRequests(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'pending'";
        return countQuery(sql, parentId);
    }
    
    @Override
    public int getOverdueBooksCount(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'issued' AND due_date < NOW() AND returned_date IS NULL";
        return countQuery(sql, parentId);
    }

    /**
     * Return counts grouped by status for the parent (status -> count)
     */
    @Override
    public Map<String, Integer> getCountsByStatus(int parentId) {
        Map<String, Integer> map = new HashMap<>();
        String sql = "SELECT status, COUNT(*) AS cnt FROM requests WHERE parent_id = ? GROUP BY status";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getString("status"), rs.getInt("cnt"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    /* -------------------------
       Table/list methods
       ------------------------- */

    /**
     * Recent requests (default limit)
     */
    @Override
    public List<Request> getRecentRequests(int parentId, int limit) {
        List<Request> list = new ArrayList<>();
        String sql =
                "SELECT r.request_id, r.request_date, r.due_date, r.status, b.book_id, b.title " +
                "FROM requests r " +
                "JOIN book b ON r.book_id = b.book_id " +
                "WHERE r.parent_id = ? " +
                "ORDER BY r.request_date DESC " +
                "LIMIT ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            ps.setInt(2, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setRequestDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
                    r.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
                    r.setStatus(rs.getString("status"));
                    r.setBookId(rs.getInt("book_id"));
                    r.setBookName(rs.getString("title"));
                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Books currently issued for this parent
     */
    @Override
    public List<Request> getIssuedBooks(int parentId) {
        List<Request> list = new ArrayList<>();
        String sql =
                "SELECT r.request_id, r.issued_date, r.due_date, r.status, b.book_id, b.title " +
                "FROM requests r " +
                "JOIN book b ON r.book_id = b.book_id " +
                "WHERE r.parent_id = ? AND r.status = 'issued' " +
                "ORDER BY r.issued_date DESC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
                    r.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
                    r.setStatus(rs.getString("status"));
                    r.setBookId(rs.getInt("book_id"));
                    r.setBookName(rs.getString("title"));
                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Overdue books list (issued + due_date < now)
     */
    @Override
    public List<Request> getOverdueBooks(int parentId) {
        List<Request> list = new ArrayList<>();
        String sql =
                "SELECT r.request_id, r.issued_date, r.due_date, r.status, b.book_id, b.title, TIMESTAMPDIFF(DAY, r.due_date, NOW()) AS days_overdue " +
                "FROM requests r " +
                "JOIN book b ON r.book_id = b.book_id " +
                "WHERE r.parent_id = ? AND r.status = 'issued' AND r.due_date < NOW() AND r.returned_date IS NULL " +
                "ORDER BY days_overdue DESC LIMIT 5";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
                    r.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
                    r.setStatus(rs.getString("status"));
                    r.setBookId(rs.getInt("book_id"));
                    r.setBookName(rs.getString("title"));
                    // store days_overdue in notes temporarily (or add explicit setter if you prefer)
                    r.setNotes("Days overdue: " + rs.getInt("days_overdue"));
                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Returned books history
     */
    @Override
    public List<Request> getReturnedBooks(int parentId) {
        List<Request> list = new ArrayList<>();
        String sql =
                "SELECT r.request_id, r.issued_date, r.returned_date, b.book_id, b.title " +
                "FROM requests r " +
                "JOIN book b ON r.book_id = b.book_id " +
                "WHERE r.parent_id = ? AND r.status = 'returned' " +
                "ORDER BY r.returned_date DESC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setReturnedDate(DateUtil.formatWithSuffix(rs.getString("returned_date")));
                    r.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
                    r.setBookId(rs.getInt("book_id"));
                    r.setBookName(rs.getString("title"));
                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Upcoming due books within next `days` days
     */
    @Override
    public List<Request> getUpcomingDue(int parentId, int days) {
        List<Request> list = new ArrayList<>();
        String sql =
                "SELECT r.request_id, r.issued_date, r.due_date, b.book_id, b.title " +
                "FROM requests r " +
                "JOIN book b ON r.book_id = b.book_id " +
                "WHERE r.parent_id = ? AND r.status = 'issued' AND r.due_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY) " +
                "ORDER BY r.due_date ASC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);
            ps.setInt(2, days);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
                    r.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
                    r.setBookId(rs.getInt("book_id"));
                    r.setBookName(rs.getString("title"));
                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    

//    ----------------------------------------------------------------------------------------------
//										Admin Dashboard Metrics
    
    @Override
    public int getTotalUsersForAdmin() {
        String sql = "SELECT COUNT(*) FROM User";
        return countQuery(sql);
    }

    
    @Override
    public int getTotalBooksIssuedForAdmin() {
        String sql = "SELECT COUNT(*) FROM requests WHERE status = 'issued'";
        return countQuery(sql);
    }

    @Override
    public int getTotalBooksReadForAdmin() {
        String sql = "SELECT COUNT(*) FROM requests WHERE status = 'returned'";
        return countQuery(sql);
    }

    @Override
    public int getUsersWaitingForApproval() {
        String sql = "SELECT COUNT(*) FROM User where isApproved = false";
        return countQuery(sql);
    }
    
    @Override
    public int getInactiveUsersForAdmin() {
        String sql = """
            SELECT COUNT(*)
            FROM User WHERE is_active = false
        """;
        return countQuery(sql);
    }
    
    @Override
    public int getOverdueBooksForAdmin() {
        String sql = """
            SELECT COUNT(*)
            FROM requests
            WHERE status = 'issued'
              AND due_date < NOW()
              AND returned_date IS NULL
        """;
        return countQuery(sql);
    }
    
    @Override
    public int getTotalBooksAvailableForAdmin() {
        String sql = "SELECT SUM(available_copies) FROM book";
        return countQuery(sql);
    }

    @Override
    public int getNewBooksRequestedToday() {
        String sql = "SELECT COUNT(*) FROM requests";
        return countQuery(sql);
    }
    
    @Override
    public List<Request> getUpcomingDueRequestForAdmin(int days) {
        List<Request> list = new ArrayList<>();
        String sql ="SELECT r.request_id, r.issued_date, r.due_date, b.book_id, b.title " +
                "FROM requests r " +
                "JOIN book b ON r.book_id = b.book_id " +
                "WHERE r.status = 'issued' AND r.due_date BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL ? DAY) " +
                "ORDER BY r.due_date ASC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, days);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setIssuedDate(rs.getString("issued_date"));
                    r.setDueDate(rs.getString("due_date"));
                    r.setBookId(rs.getInt("book_id"));
                    r.setBookName(rs.getString("title"));
                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<Request> getTodayReturnRequestForAdmin() {
        List<Request> list = new ArrayList<>();
        String sql =
        	    "SELECT r.request_id, r.issued_date, r.due_date, r.status, " +
        	    "b.book_id, b.title, p.parent_name " +
        	    "FROM requests r " +
        	    "JOIN book b ON r.book_id = b.book_id " +
        	    "JOIN parent p ON r.parent_id = p.parent_id " +
        	    "WHERE r.status = 'issued' " +
        	    "AND r.due_date >= CURDATE() " +
        	    "AND r.due_date < DATE_ADD(CURDATE(), INTERVAL 1 DAY) " +
        	    "ORDER BY r.due_date ASC";


        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setParentName(rs.getString("parent_name"));
                    r.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
                    r.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
                    r.setBookId(rs.getInt("book_id"));
                    r.setBookName(rs.getString("title"));
                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<Request> getRecentBookIssuesForAdmin() {
        List<Request> list = new ArrayList<>();
        String sql =
                "SELECT r.request_id, r.issued_date, r.due_date, " +
                "b.book_id, b.title, " +
                "p.parent_id, p.parent_name " +
                "FROM requests r " +
                "JOIN book b ON r.book_id = b.book_id " +
                "JOIN parent p ON r.parent_id = p.parent_id " +
                "WHERE r.status = 'issued' " +
                "ORDER BY r.due_date ASC LIMIT 5";


        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setParentName(rs.getString("parent_name"));
                    r.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
                    r.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
                    r.setBookId(rs.getInt("book_id"));
                    r.setBookName(rs.getString("title"));
                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<ParentUser> getUsersForApprovalForAdmin() {
        List<ParentUser> list = new ArrayList<>();
        String sql ="SELECT u.*, p.* FROM user u INNER JOIN parent p ON u.user_id = p.user_id WHERE u.isApproved = false";


        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                	ParentUser parentUser = new ParentUser();
                	parentUser.setParentName(rs.getString("parent_name"));
                	parentUser.setEmail(rs.getString("email"));
                    list.add(parentUser);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public List<Request> getOverdueBooksAdmin() {
        List<Request> list = new ArrayList<>();
        String sql =
        	    "SELECT r.request_id, r.issued_date, r.due_date, r.status, " +
        	    "b.book_id, b.title, p.parent_name, " +
        	    "TIMESTAMPDIFF(DAY, r.due_date, NOW()) AS days_overdue " +
        	    "FROM requests r " +
        	    "JOIN book b ON r.book_id = b.book_id " +
        	    "JOIN parent p ON r.parent_id = p.parent_id " +
        	    "WHERE r.status = 'issued' " +
        	    "AND r.due_date < NOW() " +
        	    "AND r.returned_date IS NULL " +
        	    "ORDER BY days_overdue DESC " +
        	    "LIMIT 5";


        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setParentName(rs.getString("parent_name"));
                    r.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
                    r.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
                    r.setStatus(rs.getString("status"));
                    r.setBookId(rs.getInt("book_id"));
                    r.setBookName(rs.getString("title"));
                    // store days_overdue in notes temporarily (or add explicit setter if you prefer)
                    //r.setNotes("Days overdue: " + rs.getInt("days_overdue"));
                    list.add(r);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }   
}
