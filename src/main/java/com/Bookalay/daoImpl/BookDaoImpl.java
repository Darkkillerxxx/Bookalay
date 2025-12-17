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
import com.Bookalay.util.DbUtil;

public class BookDaoImpl implements BookDao {

	@Override
	public List<Book> fetchAvailableBooks(String search, String interests, String genres,boolean isAdmin) {

		List<Book> books = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DbUtil.getConnection();
			StringBuilder query;
			if(isAdmin) {
				query = new StringBuilder("SELECT * FROM BOOK");
			}else {
				query = new StringBuilder("SELECT * FROM BOOK WHERE is_available = true");
			}

			List<Object> params = new ArrayList<>();

			// ✔ SEARCH (optional)
			if (search != null && !search.trim().isEmpty()) {
				query.append(" AND (title LIKE ? OR author LIKE ?)");
				params.add("%" + search + "%");
				params.add("%" + search + "%");
			}

			// ✔ GENRE FILTER (comma separated)
			if (genres != null && !genres.trim().isEmpty()) {
				String[] genreArray = genres.split(",");
				query.append(" AND genre IN (");
				query.append("?, ".repeat(genreArray.length));
				query.setLength(query.length() - 2); // remove last comma
				query.append(")");

				for (String g : genreArray) {
					params.add(g.trim());
				}
			}

			// ✔ INTEREST FILTER (comma separated)
			if (interests != null && !interests.trim().isEmpty()) {
				String[] interestArray = interests.split(",");
				query.append(" AND interest IN (");
				query.append("?, ".repeat(interestArray.length));
				query.setLength(query.length() - 2);
				query.append(")");

				for (String in : interestArray) {
					params.add(in.trim());
				}
			}

			ps = conn.prepareStatement(query.toString());

			// Set parameters safely
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

				Timestamp ts = rs.getTimestamp("date_added");
				if (ts != null) {
					book.setDateAdded(ts.toLocalDateTime());
				}

				books.add(book);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ignored) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (Exception ignored) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ignored) {
			}
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

					Timestamp ts = rs.getTimestamp("date_added");
					if (ts != null) {
						book.setDateAdded(ts.toLocalDateTime());
					}
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
	public List<Book> recommendBooksForParent(int parentId) {

	    List<Book> books = new ArrayList<>();

	    String sql =
	        "SELECT DISTINCT b.* " +
	        "FROM book b " +
	        "JOIN child c ON c.parent_id = ? " +
	        "WHERE b.is_available = 1 " +
	        "  AND b.available_copies > 0 " +
	        "  AND ( " +
	        "        CONCAT(',', LOWER(c.interests), ',') LIKE CONCAT('%,', LOWER(b.interest), ',%') " +
	        "     OR CONCAT(',', LOWER(c.genres), ',') LIKE CONCAT('%,', LOWER(b.genre), ',%') " +
	        "      ) " +
	        "  AND b.book_id NOT IN ( " +
	        "        SELECT r.book_id " +
	        "        FROM requests r " +
	        "        WHERE r.parent_id = ? " +
	        "          AND r.status IN ('issued', 'returned') " +
	        "      )";

	    try (Connection conn = DbUtil.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, parentId);
	        ps.setInt(2, parentId);

	        try (ResultSet rs = ps.executeQuery()) {
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
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return books;
	}
}
