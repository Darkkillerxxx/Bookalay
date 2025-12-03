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
import com.Bookalay.pojo.Request;
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

    public int getTotalRequests(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ?";
        return countQuery(sql, parentId);
    }

    public int getTotalApproved(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'approved'";
        return countQuery(sql, parentId);
    }

    public int getBooksCurrentlyIssued(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'issued'";
        return countQuery(sql, parentId);
    }

    public int getBooksReturned(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'returned'";
        return countQuery(sql, parentId);
    }

    public int getPendingRequests(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'pending'";
        return countQuery(sql, parentId);
    }

    public int getOverdueBooksCount(int parentId) {
        String sql = "SELECT COUNT(*) FROM requests WHERE parent_id = ? AND status = 'issued' AND due_date < NOW() AND returned_date IS NULL";
        return countQuery(sql, parentId);
    }

    /**
     * Return counts grouped by status for the parent (status -> count)
     */
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
                    r.setRequestDate(rs.getString("request_date"));
                    r.setDueDate(rs.getString("due_date"));
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
                    r.setIssuedDate(rs.getString("issued_date"));
                    r.setDueDate(rs.getString("due_date"));
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
    public List<Request> getOverdueBooks(int parentId) {
        List<Request> list = new ArrayList<>();
        String sql =
                "SELECT r.request_id, r.issued_date, r.due_date, r.status, b.book_id, b.title, TIMESTAMPDIFF(DAY, r.due_date, NOW()) AS days_overdue " +
                "FROM requests r " +
                "JOIN book b ON r.book_id = b.book_id " +
                "WHERE r.parent_id = ? AND r.status = 'issued' AND r.due_date < NOW() AND r.returned_date IS NULL " +
                "ORDER BY days_overdue DESC";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, parentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("request_id"));
                    r.setIssuedDate(rs.getString("issued_date"));
                    r.setDueDate(rs.getString("due_date"));
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
                    r.setIssuedDate(rs.getString("issued_date"));
                    r.setReturnedDate(rs.getString("returned_date"));
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
}
