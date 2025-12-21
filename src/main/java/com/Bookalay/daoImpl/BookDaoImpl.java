package com.Bookalay.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.Bookalay.dao.BookDao;
import com.Bookalay.pojo.Book;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.util.DateUtil;
import com.Bookalay.util.DbUtil;

public class BookDaoImpl implements BookDao {

	@Override
	public List<Book> fetchAvailableBooks(String search, String interests, String genres, boolean isAdmin) {

	    List<Book> books = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = DbUtil.getConnection();

	        StringBuilder query = new StringBuilder("SELECT * FROM BOOK WHERE 1=1");

	        if (!isAdmin) {
	            query.append(" AND is_available = true");
	        }

	        List<Object> params = new ArrayList<>();

	        // 🔍 SEARCH
	        if (search != null && !search.trim().isEmpty()) {
	            query.append(" AND (title LIKE ? OR author LIKE ?)");
	            params.add("%" + search + "%");
	            params.add("%" + search + "%");
	        }

	        if (genres != null && !genres.trim().isEmpty()) {
	            String[] genreArray = genres.split(",");

	            query.append(" AND (");

	            for (int i = 0; i < genreArray.length; i++) {
	                query.append(" genre LIKE ? ");
	                if (i < genreArray.length - 1) {
	                    query.append(" OR ");
	                }
	                params.add("%" + genreArray[i].trim() + "%");
	            }

	            query.append(")");
	        }


	        if (interests != null && !interests.trim().isEmpty()) {
	            String[] interestArray = interests.split(",");

	            query.append(" AND (");

	            for (int i = 0; i < interestArray.length; i++) {
	                query.append(" interest LIKE ? ");
	                if (i < interestArray.length - 1) {
	                    query.append(" OR ");
	                }
	                params.add("%" + interestArray[i].trim() + "%");
	            }

	            query.append(")");
	        }


	        System.out.println("Final Query: " + query);

	        ps = conn.prepareStatement(query.toString());

	        for (int i = 0; i < params.size(); i++) {
	            ps.setObject(i + 1, params.get(i));
	        }

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Book book = new Book();
	            book.setBookId(rs.getInt("book_id"));
	            book.setTitle(rs.getString("title"));
	            book.setAuthor(rs.getString("author"));
	            book.setInterest(rs.getString("interest"));
	            book.setGenre(rs.getString("genre"));
	            book.setReadingDifficulty(rs.getString("reading_difficulty"));
	            book.setSeriesName(rs.getString("series_name"));
	            book.setPageCount(rs.getInt("page_count"));
	            book.setSummary(rs.getString("summary"));
	            book.setCoverArt(rs.getString("cover_art"));
	            book.setAvailable(rs.getBoolean("is_available"));
	            book.setTotalCopies(rs.getInt("total_copies"));
	            book.setAvailableCopies(rs.getInt("available_copies"));
	            books.add(book);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
	        try { if (ps != null) ps.close(); } catch (Exception ignored) {}
	        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
	    }

	    return books;
	}


	@Override
	public Book getBookById(int bookId) {
		Book book = null;
		String query = "SELECT * FROM BOOK WHERE book_id = ?";

		try (Connection conn = DbUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {

			ps.setInt(1, bookId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					book = new Book();
					book.setBookId(rs.getInt("book_id"));
					book.setTitle(rs.getString("title"));
					book.setAuthor(rs.getString("author"));
					book.setInterest(rs.getString("interest"));
					book.setGenre(rs.getString("genre"));
					book.setReadingDifficulty(rs.getString("reading_difficulty"));
					book.setSeriesName(rs.getString("series_name"));
					book.setPageCount(rs.getInt("page_count"));
					book.setSummary(rs.getString("summary"));
					book.setCoverArt(rs.getString("cover_art"));
					book.setAvailable(rs.getBoolean("is_available"));
					book.setTotalCopies(rs.getInt("total_copies"));
					book.setAvailableCopies(rs.getInt("available_copies"));
					book.setDateAdded(DateUtil.formatWithSuffix(rs.getTimestamp("date_added").toLocalDateTime()));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return book;
	}

	@Override
	public Transaction createBook(Book book) {

		Transaction transaction = new Transaction();

		String sql = "INSERT INTO BOOK (" + "title, author, interest, genre, reading_difficulty, series_name, "
				+ "page_count, summary, cover_art, is_available, total_copies, " + "available_copies, date_added"
				+ ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DbUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setString(3, book.getInterest());
			ps.setString(4, book.getGenre());
			ps.setString(5, book.getReadingDifficulty());
			ps.setString(6, book.getSeriesName());

			if (book.getPageCount() > 0) {
				ps.setInt(7, book.getPageCount());
			} else {
				ps.setNull(7, java.sql.Types.INTEGER);
			}

			ps.setString(8, book.getSummary());
			ps.setString(9, book.getCoverArt()); // URL
			ps.setBoolean(10, book.isAvailable());
			ps.setInt(11, book.getTotalCopies());
			ps.setInt(12, book.getAvailableCopies());

			if (book.getDateAdded() != null) {
				ps.setTimestamp(13, Timestamp.valueOf(book.getDateAdded()));
			} else {
				ps.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
			}

			int rows = ps.executeUpdate();

			if (rows > 0) {
				transaction.setSuccess(true);
				transaction.setMessage("Book added successfully.");
			} else {
				transaction.setSuccess(false);
				transaction.setMessage("Failed to add book.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			transaction.setSuccess(false);
			transaction.setMessage("Database error: " + e.getMessage());
		}

		return transaction;
	}
	
	@Override
	public Transaction toggleBookAvailability(int bookId) {
		Transaction transaction = new Transaction();

	    String fetchSql = "SELECT is_available FROM BOOK WHERE book_id = ?";
	    String updateSql = "UPDATE BOOK SET is_available = ? WHERE book_id = ?";

	    try (Connection conn = DbUtil.getConnection();
	         PreparedStatement fetchPs = conn.prepareStatement(fetchSql)) {

	        fetchPs.setInt(1, bookId);

	        try (ResultSet rs = fetchPs.executeQuery()) {

	            if (!rs.next()) {
	            	transaction.setSuccess(false);
					transaction.setMessage("Book added successfully.");
					
					return transaction;
	            }

	            boolean currentStatus = rs.getBoolean("is_available");
	            boolean newStatus = !currentStatus; // flip true↔false

	            try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
	                updatePs.setBoolean(1, newStatus);
	                updatePs.setInt(2, bookId);

	                int rows = updatePs.executeUpdate();
	                if(rows > 0) {
	                	transaction.setSuccess(true);
						transaction.setMessage("Book Activation/Deactivation successfull.");
	                }else {
	                	transaction.setSuccess(false);
						transaction.setMessage("Error in Activation Toggling");
	                }
	                
					return transaction;// success
	            }
	        }

	    } catch (SQLException e) {
	    	transaction.setSuccess(false);
			transaction.setMessage("Database error: " + e.getMessage());
	    }

	    return transaction;
	}
	
	@Override
	public List<Book> recommendBooksForParent(int userId) {

	    List<Book> books = new ArrayList<>();

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = DbUtil.getConnection();

	        // --------------------------------------------------
	        // 1️⃣ GET PARENT ID USING USER ID
	        // --------------------------------------------------
	        int parentId = 0;

	        String parentSql = "SELECT parent_id FROM parent WHERE user_id = ?";
	        try (PreparedStatement pps = conn.prepareStatement(parentSql)) {
	            pps.setInt(1, userId);
	            try (ResultSet prs = pps.executeQuery()) {
	                if (prs.next()) {
	                    parentId = prs.getInt("parent_id");
	                }
	            }
	        }

	        // ❌ If no parent found → return empty
	        if (parentId == 0) {
	            return books;
	        }

	        // --------------------------------------------------
	        // 2️⃣ GET CHILD INTERESTS & GENRES
	        // --------------------------------------------------
	        String childInterests = "";
	        String childGenres = "";

	        String childSql =
	                "SELECT interests, genres " +
	                "FROM child " +
	                "WHERE parent_id = ?";

	        try (PreparedStatement cps = conn.prepareStatement(childSql)) {
	            cps.setInt(1, parentId);
	            try (ResultSet crs = cps.executeQuery()) {
	                if (crs.next()) {
	                    childInterests = crs.getString("interests");
	                    childGenres = crs.getString("genres");
	                }
	            }
	        }

	        // ❌ No preferences
	        if ((childInterests == null || childInterests.isBlank()) &&
	            (childGenres == null || childGenres.isBlank())) {
	            return books;
	        }

	        // --------------------------------------------------
	        // 3️⃣ BUILD RECOMMENDATION QUERY
	        // --------------------------------------------------
	        StringBuilder query = new StringBuilder(
	                "SELECT DISTINCT b.* " +
	                "FROM book b " +
	                "WHERE b.is_available = 1 " +
	                "AND b.available_copies > 0 "
	        );

	        List<Object> params = new ArrayList<>();
	        query.append(" AND (");

	        boolean first = true;

	        // 🎯 INTEREST MATCH
	        if (childInterests != null && !childInterests.isBlank()) {
	            for (String interest : childInterests.split(",")) {
	                if (!first) query.append(" OR ");
	                query.append("LOWER(b.interest) LIKE ?");
	                params.add("%" + interest.trim().toLowerCase() + "%");
	                first = false;
	            }
	        }

	        // 🎭 GENRE MATCH
	        if (childGenres != null && !childGenres.isBlank()) {
	            for (String genre : childGenres.split(",")) {
	                if (!first) query.append(" OR ");
	                query.append("LOWER(b.genre) LIKE ?");
	                params.add("%" + genre.trim().toLowerCase() + "%");
	                first = false;
	            }
	        }

	        query.append(")");

	        // 🚫 EXCLUDE ALREADY ISSUED / RETURNED
	        query.append(
	                " AND b.book_id NOT IN ( " +
	                "   SELECT r.book_id " +
	                "   FROM requests r " +
	                "   WHERE r.parent_id = ? " +
	                "   AND r.status IN ('issued','returned')" +
	                " ) LIMIT 6"
	        );

	        params.add(parentId);

	        // --------------------------------------------------
	        // 4️⃣ EXECUTE
	        // --------------------------------------------------
	        ps = conn.prepareStatement(query.toString());

	        for (int i = 0; i < params.size(); i++) {
	            ps.setObject(i + 1, params.get(i));
	        }

	        rs = ps.executeQuery();

	        while (rs.next()) {
	            Book book = new Book();
	            book.setBookId(rs.getInt("book_id"));
	            book.setTitle(rs.getString("title"));
	            book.setAuthor(rs.getString("author"));
	            book.setInterest(rs.getString("interest"));
	            book.setGenre(rs.getString("genre"));
	            book.setCoverArt(rs.getString("cover_art"));
	            book.setAvailableCopies(rs.getInt("available_copies"));
	            books.add(book);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
	        try { if (ps != null) ps.close(); } catch (Exception ignored) {}
	        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
	    }

	    return books;
	}

}
