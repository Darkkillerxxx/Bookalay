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
import com.Bookalay.util.DateUtil;
import com.Bookalay.util.DbUtil;
import com.Bookalay.util.FunctUtil;

public class RequestDaoImpl implements RequestDao {
	
	@Override
	public List<Request> fetchActiveRequestForParentAndBook(String userId, String bookId) {
	    List<Request> list = new ArrayList<>();

	    String sql = "SELECT r.*, "
	            + "p.name AS parent_name, p.email AS parent_email, p.phone AS parent_phone, "
	            + "c.name AS child_name, c.age AS child_age, c.gender AS child_gender, "
	            + "c.interests AS child_interests, c.reading_level AS child_reading_level, "
	            + "c.genres AS child_genres, c.reading_frequency AS child_reading_frequency, "
	            + "c.notes AS child_notes, "
	            + "b.book_name, b.author AS book_author, b.interest AS book_interest, "
	            + "b.genre AS book_genre, b.difficulty AS book_difficulty, b.series_name, "
	            + "b.page_count, b.summary, b.cover_art, b.is_available, "
	            + "b.total_copies, b.available_copies, b.date_added "
	            + "FROM requests r "
	            + "JOIN parents p ON r.parent_id = p.parent_id "
	            + "JOIN children c ON r.child_id = c.child_id "
	            + "JOIN books b ON r.book_id = b.book_id "
	            + "WHERE r.status = 'issued' "
	            + "AND r.issued_date >= CURDATE() "
	            + "AND r.parent_id = ? "
	            + "AND r.book_id = ?";

	    try (Connection con = DbUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, Integer.parseInt(userId));
	        ps.setInt(2, Integer.parseInt(bookId));

	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Request r = new Request();

	                // ===== Request Fields =====
	                r.setRequestId(rs.getInt("request_id"));
	                r.setParentId(rs.getInt("parent_id"));
	                r.setChildId(rs.getInt("child_id"));
	                r.setBookId(rs.getInt("book_id"));
	                r.setStatus(rs.getString("status"));
	                r.setRequestDate(rs.getString("request_date"));
	                r.setApprovalDate(rs.getString("approval_date"));
	                r.setIssuedDate(rs.getString("issued_date"));
	                r.setDueDate(rs.getString("due_date"));
	                r.setReturnedDate(rs.getString("returned_date"));
	                r.setNotes(rs.getString("notes"));
	                r.setApprovedBy(rs.getInt("approved_by"));

	                // NEW FIELD — request_duration
	                r.setRequestDuration(rs.getInt("request_duration"));

	                // ===== Parent Fields =====
	                r.setParentName(rs.getString("parent_name"));
	                r.setParentEmail(rs.getString("parent_email"));
	                r.setParentPhone(rs.getString("parent_phone"));

	                // ===== Child Fields =====
	                r.setChildName(rs.getString("child_name"));
	                r.setChildAge(rs.getInt("child_age"));
	                r.setChildGender(rs.getString("child_gender"));
	                r.setChildInterests(rs.getString("child_interests"));
	                r.setChildReadingLevel(rs.getString("child_reading_level"));
	                r.setChildGenres(rs.getString("child_genres"));
	                r.setChildReadingFrequency(rs.getString("child_reading_frequency"));
	                r.setChildNotes(rs.getString("child_notes"));

	                // ===== Book Fields =====
	                r.setBookName(rs.getString("book_name"));
	                r.setBookAuthor(rs.getString("book_author"));
	                r.setBookInterest(rs.getString("book_interest"));
	                r.setBookGenre(rs.getString("book_genre"));
	                r.setBookDifficulty(rs.getString("book_difficulty"));
	                r.setSeriesName(rs.getString("series_name"));
	                r.setPageCount(rs.getInt("page_count"));
	                r.setSummary(rs.getString("summary"));
	                r.setCoverArt(rs.getString("cover_art"));
	                r.setIsAvailable(rs.getInt("is_available"));
	                r.setTotalCopies(rs.getInt("total_copies"));
	                r.setAvailableCopies(rs.getInt("available_copies"));
	                r.setDateAdded(rs.getString("date_added"));

	                list.add(r);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	@Override
	public Request fetchRequestDetails(int id) {
        	Request req = new Request();
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
	                "WHERE r.request_id = ? " +
	                "ORDER BY r.request_date DESC";

	            ps = conn.prepareStatement(query);
	            ps.setInt(1, id);   // FIXED
	            rs = ps.executeQuery();

	            while (rs.next()) {


	                // Request fields
	                req.setRequestId(rs.getInt("request_id"));
	                req.setParentId(rs.getInt("parent_id"));
	                req.setChildId(rs.getInt("child_id"));
	                req.setBookId(rs.getInt("book_id"));
	                req.setStatus(rs.getString("status"));
	                
	                req.setRequestDate(DateUtil.formatWithSuffix(rs.getString("request_date")));
	                req.setApprovalDate(DateUtil.formatWithSuffix(rs.getString("approval_date")));
	                req.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
	                req.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
	                req.setReturnedDate(DateUtil.formatWithSuffix(rs.getString("returned_date")));
	                
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
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
	            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
	            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
	        }
	        return req;
	}
	
	@Override
	public List<Request> fetchRequestsForBookId(String bookId) {

	    List<Request> list = new ArrayList<>();

	    String query =
	        "SELECT p.parent_name, " +
	        "       b.title AS book_name, " +
	        "       r.request_date, " +
	        "       r.approval_date,r.issued_date,r.due_date,r.returned_date, " +
	        "       r.status " +
	        "FROM requests r " +
	        "JOIN parent p ON r.parent_id = p.parent_id " +
	        "JOIN book b ON r.book_id = b.book_id " +
	        "WHERE r.book_id = ? AND (r.status = 'issued' OR r.status = 'approved')" +
	        "ORDER BY r.request_date DESC";

	    try (
	        Connection conn = DbUtil.getConnection();
	        PreparedStatement ps = conn.prepareStatement(query)
	    ) {
	    	
	    	if(bookId != null) {
		        ps.setInt(1, Integer.parseInt(bookId));
	    	}

	        try (ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                Request req = new Request();

	                req.setParentName(rs.getString("parent_name"));
	                req.setBookName(rs.getString("book_name"));
	                req.setRequestDate(DateUtil.formatWithSuffix(rs.getString("request_date")));
	                req.setApprovalDate(DateUtil.formatWithSuffix(rs.getString("approval_date")));
	                req.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
	                req.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
	                req.setReturnedDate(DateUtil.formatWithSuffix(rs.getString("returned_date")));
	                req.setStatus(rs.getString("status"));

	                list.add(req);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	@Override
	public List<Request> fetchActiveRequests(boolean isAdmin, String searchText) {

	    List<Request> list = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = DbUtil.getConnection();

	        String query;

	        if (isAdmin) {
	            query =
	                "SELECT r.request_id, p.parent_name, r.status, " +
	                "       b.book_id, b.title AS book_name, r.request_date " +
	                "FROM requests r " +
	                "JOIN parent p ON r.parent_id = p.parent_id " +
	                "JOIN book b ON r.book_id = b.book_id " +
	                "WHERE (p.parent_name LIKE ? OR b.title LIKE ?) " +
	                "ORDER BY r.request_date DESC";

	            ps = conn.prepareStatement(query);
	            if(searchText == null) {
	            	searchText="";
	            }
	            ps.setString(1, "%" + searchText + "%");
	            ps.setString(2, "%" + searchText + "%");

	        } else {
	            query =
	                "SELECT r.request_id, p.parent_name, r.status, " +
	                "       b.book_id, b.title AS book_name, r.request_date " +
	                "FROM requests r " +
	                "JOIN parent p ON r.parent_id = p.parent_id " +
	                "JOIN book b ON r.book_id = b.book_id " +
	                "WHERE r.issued_date IS NOT NULL " +
	                "AND r.returned_date IS NULL " +
	                "ORDER BY r.request_date DESC";

	            ps = conn.prepareStatement(query);
	        }

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Request req = new Request();
	            req.setRequestId(rs.getInt("request_id"));
	            req.setBookId(rs.getInt("book_id"));
	            req.setParentName(rs.getString("parent_name"));
	            req.setBookName(rs.getString("book_name"));
                req.setRequestDate(DateUtil.formatWithSuffix(rs.getString("request_date")));
	            req.setStatus(rs.getString("status"));
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

            Integer parentId = FunctUtil.getParentIdByUserId(id);

            if (parentId == null) {
                return list;
            }
            


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
            ps.setInt(1, parentId); 
            rs = ps.executeQuery();

            while (rs.next()) {

                Request req = new Request();

                // Request fields
                req.setRequestId(rs.getInt("request_id"));
                req.setParentId(rs.getInt("parent_id"));
                req.setChildId(rs.getInt("child_id"));
                req.setBookId(rs.getInt("book_id"));
                req.setStatus(rs.getString("status"));
                req.setRequestDate(DateUtil.formatWithSuffix(rs.getString("request_date")));
                req.setApprovalDate(DateUtil.formatWithSuffix(rs.getString("approval_date")));
                req.setIssuedDate(DateUtil.formatWithSuffix(rs.getString("issued_date")));
                req.setDueDate(DateUtil.formatWithSuffix(rs.getString("due_date")));
                req.setReturnedDate(DateUtil.formatWithSuffix(rs.getString("returned_date")));
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
                    (parent_id, child_id, book_id,request_date ,due_date, notes)
                VALUES (?, ?, ?, ?, ?, ?);
            """;

            ps = conn.prepareStatement(insertSql);
            ps.setInt(1, parentId);
            ps.setInt(2, childId);
            ps.setInt(3, Integer.parseInt(bookId));
            ps.setTimestamp(4, new java.sql.Timestamp(requestDate.getTime()));
            ps.setTimestamp(5, new java.sql.Timestamp(requestDate.getTime() + (14L * 24 * 60 * 60 * 1000)));
            ps.setString(6, additionalNotes);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                t.setSuccess(true);
                t.setMessage("Yor Request is raised Succesfully !!! Please wait for the Admin to Approve your Request");
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
    public Transaction rejectRequest(int requestId,String notes) {

        Transaction t = new Transaction();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtil.getConnection();

            /* -----------------------------------
               Reject the request
            ------------------------------------- */
            String sql =
                "UPDATE requests SET status = 'rejected',notes = ? WHERE request_id = ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, notes);
            ps.setInt(2, requestId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                t.setSuccess(true);
                t.setMessage("Request rejected successfully.");
            } else {
                t.setSuccess(false);
                t.setMessage("Request not found.");
            }

        } catch (Exception e) {
            t.setSuccess(false);
            t.setMessage("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return t;
    }
    
    @Override
    public Transaction issueRequest(int requestId) {

        Transaction t = new Transaction();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtil.getConnection();

            /* -----------------------------------
               Issue the book
            ------------------------------------- */
            String sql = """
                UPDATE requests
                SET status = 'issued',
                    issued_date = CURRENT_DATE,
                    due_date = DATE_ADD(CURRENT_DATE, INTERVAL 14 DAY)
                WHERE request_id = ?
            """;

            ps = conn.prepareStatement(sql);
            ps.setInt(1, requestId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                t.setSuccess(true);
                t.setMessage("Book issued successfully.");
            } else {
                t.setSuccess(false);
                t.setMessage("Request not found.");
            }

        } catch (Exception e) {
            t.setSuccess(false);
            t.setMessage("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }

        return t;
    }
    
    @Override
    public Transaction approveRequest(int requestId) {

        Transaction t = new Transaction();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConnection();
            conn.setAutoCommit(false); // IMPORTANT (transaction safety)

            /* -----------------------------------
               1. Get book_id, request_date & available copies
            ------------------------------------- */
            String baseSql = """
                SELECT r.book_id, r.request_date, b.available_copies
                FROM requests r
                JOIN book b ON r.book_id = b.book_id
                WHERE r.request_id = ?
            """;

            ps = conn.prepareStatement(baseSql);
            ps.setInt(1, requestId);
            rs = ps.executeQuery();

            int bookId;
            Date requestDate;
            int availableCopies;

            if (rs.next()) {
                bookId = rs.getInt("book_id");
                requestDate = rs.getDate("request_date");
                availableCopies = rs.getInt("available_copies");
            } else {
                t.setSuccess(false);
                t.setMessage("Request not found.");
                return t;
            }

            rs.close();
            ps.close();

            /* -----------------------------------
               2. Copies available → approve + reduce book copies
            ------------------------------------- */
            if (availableCopies > 0) {

                // 2.1 Approve request
                String approveSql =
                        "UPDATE requests SET status = 'approved' WHERE request_id = ?";
                ps = conn.prepareStatement(approveSql);
                ps.setInt(1, requestId);
                ps.executeUpdate();
                ps.close();

                // 2.2 Reduce available copies in BOOK table
                String updateBookSql =
                        "UPDATE book SET available_copies = available_copies - 1 WHERE book_id = ?";
                ps = conn.prepareStatement(updateBookSql);
                ps.setInt(1, bookId);
                ps.executeUpdate();

                conn.commit();

                t.setSuccess(true);
                t.setMessage("Request approved successfully.");
                return t;
            }

            /* -----------------------------------
               3. Check last due date for future availability
            ------------------------------------- */
            String dueSql = """
                SELECT MAX(due_date) AS last_due
                FROM requests
                WHERE book_id = ?
                  AND status IN ('issued', 'approved')
            """;

            ps = conn.prepareStatement(dueSql);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            Date lastDueDate = null;
            if (rs.next()) {
                lastDueDate = rs.getDate("last_due");
            }

            rs.close();
            ps.close();

            /* -----------------------------------
               4. Approve only if request is AFTER last due
            ------------------------------------- */
            if (lastDueDate != null && requestDate.after(lastDueDate)) {

                String approveSql =
                        "UPDATE requests SET status = 'approved' WHERE request_id = ?";
                ps = conn.prepareStatement(approveSql);
                ps.setInt(1, requestId);
                ps.executeUpdate();

                conn.commit();

                t.setSuccess(true);
                t.setMessage("Request approved (future availability confirmed).");
            } else {
                conn.rollback();
                t.setSuccess(false);
                t.setMessage("No copies available to approve this request.");
            }

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ignored) {}
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
    public Transaction returnRequest(int requestId) {

        Transaction t = new Transaction();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConnection();
            conn.setAutoCommit(false); // transaction start

            int bookId = 0;

            /* -----------------------------------
               1. Get Book ID from Request
            ------------------------------------- */
            String getBookSql = """
                SELECT book_id
                FROM requests
                WHERE request_id = ? AND status = 'issued'
            """;

            ps = conn.prepareStatement(getBookSql);
            ps.setInt(1, requestId);
            rs = ps.executeQuery();

            if (rs.next()) {
                bookId = rs.getInt("book_id");
            } else {
                t.setSuccess(false);
                t.setMessage("Invalid request or book not issued.");
                return t;
            }

            rs.close();
            ps.close();

            /* -----------------------------------
               2. Update Request → returned
            ------------------------------------- */
            String updateRequestSql = """
                UPDATE requests
                SET status = 'returned',
                    returned_date = CURRENT_TIMESTAMP
                WHERE request_id = ?
            """;

            ps = conn.prepareStatement(updateRequestSql);
            ps.setInt(1, requestId);
            ps.executeUpdate();
            ps.close();

            /* -----------------------------------
               3. Increase Available Copies
            ------------------------------------- */
            String updateBookSql = """
                UPDATE book
                SET available_copies = available_copies + 1
                WHERE book_id = ?
            """;

            ps = conn.prepareStatement(updateBookSql);
            ps.setInt(1, bookId);
            ps.executeUpdate();

            conn.commit(); // ✅ success

            t.setSuccess(true);
            t.setMessage("Book returned successfully.");

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (Exception ignored) {}
            t.setSuccess(false);
            t.setMessage("Error while returning book: " + e.getMessage());
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
