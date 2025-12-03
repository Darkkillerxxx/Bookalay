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
import com.Bookalay.util.DbUtil;

public class BookDaoImpl implements BookDao {

	@Override
	public List<Book> fetchAvailableBooks(String search, String interests, String genres) {

		List<Book> books = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			conn = DbUtil.getConnection();

			StringBuilder query = new StringBuilder("SELECT * FROM BOOK WHERE is_available = TRUE");

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
}
