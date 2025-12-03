package com.Bookalay.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Bookalay.dao.RequestDao;
import com.Bookalay.pojo.Request;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.util.DbUtil;

public class RequestDaoImpl implements RequestDao {

    @Override
    public List<Request> fetchActiveRequests() {

        List<Request> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = DbUtil.getConnection();

            String query =
                    "SELECT p.parent_name, b.title AS book_name, r.request_date " +
                    "FROM requests r " +
                    "JOIN parent p ON r.parent_id = p.parent_id " +
                    "JOIN book b ON r.book_id = b.book_id " +
                    "WHERE r.issued_date IS NOT NULL " +
                    "AND r.returned_date IS NULL";

            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {

                Request req = new Request();

                req.setParentName(rs.getString("parent_name"));
                req.setBookName(rs.getString("book_name"));
                req.setRequestDate(rs.getString("request_date"));

                list.add(req);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return list;
    }
    
    @Override
    public List<Request> fetchRequestsDetailsById(int id) {

        List<Request> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            conn = DbUtil.getConnection();

            String query = 
                "SELECT r.*, " +
                "p.parent_id, p.user_id AS p_user_id, p.parent_name, p.email AS p_email, p.phone, p.registration_date AS p_reg_date, " +
                "u.user_id, u.username, u.user_type, u.last_login, u.is_active, u.created_date, u.isApproved, " +
                "c.child_id, c.child_name, c.age, c.gender, c.interests, c.reading_level, c.genres, c.reading_frequency, c.notes AS child_notes, " +
                "b.book_id, b.title, b.author, b.interest, b.genre, b.reading_difficulty, b.series_name, b.page_count, b.summary, b.cover_art, " +
                "b.is_available, b.total_copies, b.available_copies, b.date_added " +
                "FROM requests r " +
                "JOIN parent p ON r.parent_id = p.parent_id " +
                "JOIN user u ON p.user_id = u.user_id " +
                "JOIN child c ON r.child_id = c.child_id " +
                "JOIN book b ON r.book_id = b.book_id " +
                "WHERE r.parent_id = ? " +
                "ORDER BY r.request_date DESC";

            ps = conn.prepareStatement(query);
            ps.setInt(1, id);   // FIXED
            rs = ps.executeQuery();

            while (rs.next()) {

                Request req = new Request();

                // Request fields
                req.setRequestId(rs.getInt("request_id"));
                req.setParentId(rs.getInt("parent_id"));
                req.setChildId(rs.getInt("child_id"));
                req.setBookId(rs.getInt("book_id"));
                req.setStatus(rs.getString("status"));
                req.setRequestDate(rs.getString("request_date"));
                req.setApprovalDate(rs.getString("approval_date"));
                req.setIssuedDate(rs.getString("issued_date"));
                req.setDueDate(rs.getString("due_date"));
                req.setReturnedDate(rs.getString("returned_date"));
                req.setNotes(rs.getString("notes"));
                req.setApprovedBy(rs.getInt("approved_by"));

                // Parent fields
                req.setParentName(rs.getString("parent_name"));
                req.setParentEmail(rs.getString("p_email"));
                req.setParentPhone(rs.getString("phone"));

                // Child fields
                req.setChildName(rs.getString("child_name"));
                req.setChildAge(rs.getInt("age"));
                req.setChildGender(rs.getString("gender"));
                req.setChildInterests(rs.getString("interests"));
                req.setChildReadingLevel(rs.getString("reading_level"));
                req.setChildGenres(rs.getString("genres"));
                req.setChildReadingFrequency(rs.getString("reading_frequency"));
                req.setChildNotes(rs.getString("child_notes"));

                // Book fields
                req.setBookName(rs.getString("title"));
                req.setBookAuthor(rs.getString("author"));
                req.setBookInterest(rs.getString("interest"));
                req.setBookGenre(rs.getString("genre"));
                req.setBookDifficulty(rs.getString("reading_difficulty"));
                req.setSeriesName(rs.getString("series_name"));
                req.setPageCount(rs.getInt("page_count"));
                req.setSummary(rs.getString("summary"));
                req.setCoverArt(rs.getString("cover_art"));
                req.setIsAvailable(rs.getInt("is_available"));
                req.setTotalCopies(rs.getInt("total_copies"));
                req.setAvailableCopies(rs.getInt("available_copies"));
                req.setDateAdded(rs.getString("date_added"));

                // User fields
                req.setUsername(rs.getString("username"));
                req.setUserType(rs.getString("user_type"));
                req.setLastLogin(rs.getString("last_login"));
                req.setUserActive(rs.getInt("is_active"));
                req.setUserCreatedDate(rs.getString("created_date"));
                req.setUserApproved(rs.getInt("isApproved"));

                list.add(req);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return list;
    }

    @Override
    public Transaction raiseRequest(int userId, String bookId, Date requestDate, String additionalNotes) {

        Transaction t = new Transaction();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConnection();

            /* -----------------------------------
               1. Get Parent ID using User ID
            ------------------------------------- */
            String getParent = "SELECT parent_id FROM parent WHERE user_id = ?";
            ps = conn.prepareStatement(getParent);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            int parentId = 0;
            if (rs.next()) parentId = rs.getInt("parent_id");
            else {
                t.setSuccess(false);
                t.setMessage("Parent record not found.");
                return t;
            }
            rs.close();
            ps.close();

            /* -----------------------------------
               2. Get Child ID from Parent ID
            ------------------------------------- */
            String getChild = "SELECT child_id FROM child WHERE parent_id = ? LIMIT 1";
            ps = conn.prepareStatement(getChild);
            ps.setInt(1, parentId);
            rs = ps.executeQuery();

            int childId = 0;
            if (rs.next()) childId = rs.getInt("child_id");
            else {
                t.setSuccess(false);
                t.setMessage("No child found for this parent.");
                return t;
            }
            rs.close();
            ps.close();

            /* -----------------------------------
               3. Insert into requests table
            ------------------------------------- */
            String insertSql = """
                INSERT INTO requests
                    (parent_id, child_id, book_id, due_date, notes)
                VALUES (?, ?, ?, ?, ?);
            """;

            ps = conn.prepareStatement(insertSql);
            ps.setInt(1, parentId);
            ps.setInt(2, childId);
            ps.setInt(3, Integer.parseInt(bookId));
            ps.setTimestamp(4, new java.sql.Timestamp(requestDate.getTime()));
            ps.setString(5, additionalNotes);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                t.setSuccess(true);
                t.setMessage("Request raised successfully!");
            } else {
                t.setSuccess(false);
                t.setMessage("Could not raise request.");
            }

        } catch (Exception e) {
            t.setSuccess(false);
            t.setMessage("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return t;
    }

	@Override
	public List<Request> raiseRequestById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
