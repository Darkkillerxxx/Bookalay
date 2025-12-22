package com.Bookalay.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Bookalay.dao.UserDao;
import com.Bookalay.pojo.ChildUser;
import com.Bookalay.pojo.ParentUser;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.pojo.User;
import com.Bookalay.pojo.UserProfile;
import com.Bookalay.util.DbUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public User checkLogin(String Username, String Password) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;

		String query = "SELECT * FROM user WHERE username = ? AND password_hash = ? AND isApproved = true AND is_active = 1";

		try {
			conn = DbUtil.getConnection();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, Username);
			preparedStatement.setString(2, Password);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				User user = new User();
				user.setUserId(rs.getInt("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPasswordHash(rs.getString("password_hash"));
				user.setUserType(rs.getString("user_type"));
				user.setLastLogin(rs.getString("last_login"));
				user.setActive(rs.getBoolean("is_active"));
				user.setCreatedDate(rs.getString("created_date"));

				return user;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Transaction insertUser(String Username, String Password, String childName, String age, String gender,
			String interests, String parentName, String phoneNumber, String email, String readingLevel, String genres,
			String readingFrequency, String notes) {

		Connection conn = null;
		PreparedStatement psUser = null;
		PreparedStatement psParent = null;
		PreparedStatement psChild = null;
		ResultSet rsUser = null;
		ResultSet rsParent = null;

		Transaction transaction = new Transaction();

		try {

			conn = DbUtil.getConnection();

			// ----------------------- 1️⃣ INSERT USER -----------------------
			String sqlUser = "INSERT INTO user (username, password_hash, user_type, created_date) "
					+ "VALUES (?, ?, 'parent', NOW())";

			psUser = conn.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS);
			psUser.setString(1, Username);
			psUser.setString(2, Password);

			int userRows = psUser.executeUpdate();
			int userId = 0;

			if (userRows > 0) {
				rsUser = psUser.getGeneratedKeys();
				if (rsUser.next())
					userId = rsUser.getInt(1);
			}

			// ❌ If userId not generated → STOP
			if (userId == 0) {
				transaction.setSuccess(false);
				transaction.setMessage("User could not be created !!!");
				return transaction;
			}

			// ----------------------- 2️⃣ INSERT PARENT -----------------------
			String sqlParent = "INSERT INTO parent (user_id, parent_name, email, phone, registration_date) "
					+ "VALUES (?, ?, ?, ?, NOW())";

			psParent = conn.prepareStatement(sqlParent, PreparedStatement.RETURN_GENERATED_KEYS);
			psParent.setInt(1, userId);
			psParent.setString(2, parentName);
			psParent.setString(3, email);
			psParent.setString(4, phoneNumber);

			int parentRows = psParent.executeUpdate();
			int parentId = 0;

			if (parentRows > 0) {
				rsParent = psParent.getGeneratedKeys();
				if (rsParent.next())
					parentId = rsParent.getInt(1);
			}

			// ❌ If parent insert failed → STOP
			if (parentId == 0) {
				transaction.setSuccess(false);
				transaction.setMessage("Parent record could not be created !!! Please contact your Admin.");
				return transaction;
			}

			// ----------------------- 3️⃣ INSERT CHILD -----------------------
			String sqlChild = "INSERT INTO child (parent_id, child_name, age, gender, interests, reading_level, genres, "
					+ "reading_frequency, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			psChild = conn.prepareStatement(sqlChild);
			psChild.setInt(1, parentId);
			psChild.setString(2, childName);
			psChild.setInt(3, Integer.parseInt(age));
			psChild.setString(4, gender);
			psChild.setString(5, interests);
			psChild.setString(6, readingLevel);
			psChild.setString(7, genres);
			psChild.setString(8, readingFrequency);
			psChild.setString(9, notes);

			psChild.executeUpdate();

			// SUCCESS
			transaction.setSuccess(true);
			transaction.setMessage("Your Child Has Been Successfully Registered !!! "
					+ "Kindly wait as your record is being Approved By the Admin");

			return transaction;

		} catch (SQLException e) {
			e.printStackTrace();
			transaction.setSuccess(false);
			transaction.setMessage("An error occurred while processing your request.");
			return transaction;

		} finally {

			try {
				if (rsUser != null)
					rsUser.close();
				if (rsParent != null)
					rsParent.close();
				if (psChild != null)
					psChild.close();
				if (psParent != null)
					psParent.close();
				if (psUser != null)
					psUser.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<ParentUser> fetchParentUsersForApproval(String search) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ParentUser> nonApprovedParents = new ArrayList<>();

		String query = "SELECT p.parent_id, p.user_id, p.parent_name, p.email, p.phone, p.registration_date, "
				+ "u.username, u.user_type, u.is_active, u.created_date " + "FROM parent p "
				+ "INNER JOIN user u ON p.user_id = u.user_id " + "WHERE (u.isApproved = 0 AND u.isRejected = 0)"
				+ "AND (p.parent_name LIKE ? OR p.email LIKE ? OR u.username LIKE ?)";

		try {
			conn = DbUtil.getConnection();
			ps = conn.prepareStatement(query);

			String like = "%" + search + "%";

			ps.setString(1, like);
			ps.setString(2, like);
			ps.setString(3, like);

			rs = ps.executeQuery();

			while (rs.next()) {
				ParentUser parent = new ParentUser();

				parent.setParentId(rs.getInt("parent_id"));
				parent.setUserId(rs.getInt("user_id"));
				parent.setParentName(rs.getString("parent_name"));
				parent.setEmail(rs.getString("email"));
				parent.setPhone(rs.getString("phone"));

				parent.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());

				nonApprovedParents.add(parent);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return nonApprovedParents;
	}
	
	@Override
	public List<ParentUser> fetchAllUsers(String search) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<ParentUser> nonApprovedParents = new ArrayList<>();

		String query = "SELECT p.parent_id, p.user_id, p.parent_name, p.email, p.phone, p.registration_date, "
				+ "u.username, u.user_type, u.is_active, u.created_date " + "FROM parent p "
				+ "INNER JOIN user u ON p.user_id = u.user_id "
				+ "AND (p.parent_name LIKE ? OR p.email LIKE ? OR u.username LIKE ?)";

		try {
			conn = DbUtil.getConnection();
			ps = conn.prepareStatement(query);

			String like = "%" + search + "%";

			ps.setString(1, like);
			ps.setString(2, like);
			ps.setString(3, like);

			rs = ps.executeQuery();

			while (rs.next()) {
				ParentUser parent = new ParentUser();

				parent.setParentId(rs.getInt("parent_id"));
				parent.setUserId(rs.getInt("user_id"));
				parent.setParentName(rs.getString("parent_name"));
				parent.setEmail(rs.getString("email"));
				parent.setPhone(rs.getString("phone"));

				parent.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());

				nonApprovedParents.add(parent);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return nonApprovedParents;
	}
	
	@Override
	public List<ParentUser> activateOrDeactivateUser(String parentId, Boolean isActive) {
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    List<ParentUser> updatedParents = new ArrayList<>();

	    try {
	        conn = DbUtil.getConnection();

	        // 1. Get user_id corresponding to parent_id
	        String getUserQuery = "SELECT user_id FROM parent WHERE parent_id = ?";
	        ps = conn.prepareStatement(getUserQuery);
	        ps.setString(1, parentId);
	        rs = ps.executeQuery();

	        Integer userId = null;
	        if (rs.next()) {
	            userId = rs.getInt("user_id");
	        }
	        rs.close();
	        ps.close();

	        if (userId != null) {
	            // 2. Update user table to set is_active
	            String updateQuery = "UPDATE user SET is_active = ? WHERE user_id = ?";
	            ps = conn.prepareStatement(updateQuery);
	            ps.setBoolean(1, isActive);
	            ps.setInt(2, userId);
	            ps.executeUpdate();
	            ps.close();

	            // 3. Optionally, return the updated parent info
	            String selectQuery = "SELECT p.parent_id, p.user_id, p.parent_name, p.email, p.phone, "
	                    + "p.registration_date, u.username, u.user_type, u.is_active, u.created_date "
	                    + "FROM parent p INNER JOIN user u ON p.user_id = u.user_id WHERE p.parent_id = ?";
	            ps = conn.prepareStatement(selectQuery);
	            ps.setString(1, parentId);
	            rs = ps.executeQuery();

	            while (rs.next()) {
	                ParentUser parent = new ParentUser();
	                parent.setParentId(rs.getInt("parent_id"));
	                parent.setUserId(rs.getInt("user_id"));
	                parent.setParentName(rs.getString("parent_name"));
	                parent.setEmail(rs.getString("email"));
	                parent.setPhone(rs.getString("phone"));
	                parent.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
	                parent.setActive(rs.getBoolean("is_active")); 
	                updatedParents.add(parent);
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return updatedParents;
	}
	
	@Override
	public UserProfile fetchUserProfile(String parentId) {

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    String query =
	            "SELECT " +
	            "u.user_id, u.username, u.password_hash, u.user_type, u.last_login, " +
	            "u.is_active, u.created_date, u.isApproved, " +
	            "p.parent_id, p.parent_name, p.email, p.phone, p.registration_date, " +
	            "c.child_id, c.child_name, c.age, c.gender, c.interests, " +
	            "c.reading_level, c.genres, c.reading_frequency, c.notes " +
	            "FROM parent p " +
	            "JOIN user u ON p.user_id = u.user_id " +
	            "LEFT JOIN child c ON p.parent_id = c.parent_id " +
	            "WHERE p.user_id = ?";

	    UserProfile profile = new UserProfile();

	    try {
	        conn = DbUtil.getConnection();
	        ps = conn.prepareStatement(query);
	        ps.setString(1, parentId);
	        rs = ps.executeQuery();

	        if (rs.next()) {

	            // ===== USER =====
	            User user = new User();
	            user.setUserId(rs.getInt("user_id"));
	            user.setUsername(rs.getString("username"));
	            user.setPasswordHash(rs.getString("password_hash"));
	            user.setUserType(rs.getString("user_type"));
	            user.setLastLogin(rs.getString("last_login"));
	            user.setActive(rs.getBoolean("is_active"));
	            user.setCreatedDate(rs.getString("created_date"));

	            // ===== PARENT =====
	            ParentUser parent = new ParentUser();
	            parent.setParentId(rs.getInt("parent_id"));
	            parent.setParentName(rs.getString("parent_name"));
	            parent.setEmail(rs.getString("email"));
	            parent.setPhone(rs.getString("phone"));
	            parent.setRegistrationDate(
	                    rs.getTimestamp("registration_date") != null
	                            ? rs.getTimestamp("registration_date").toLocalDateTime()
	                            : null
	            );

	            // ===== CHILD (ALWAYS CREATED) =====
	            ChildUser child = new ChildUser();
	            child.setChildId(rs.getInt("child_id"));
	            child.setParentId(rs.getInt("parent_id"));
	            child.setChildName(rs.getString("child_name"));
	            child.setAge(rs.getInt("age"));
	            child.setGender(rs.getString("gender"));
	            child.setInterests(rs.getString("interests"));
	            child.setReadingLevel(rs.getString("reading_level"));
	            child.setGenres(rs.getString("genres"));
	            child.setReadingFrequency(rs.getString("reading_frequency"));
	            child.setNotes(rs.getString("notes"));

	            profile.setUser(user);
	            profile.setParent(parent);
	            profile.setChild(child);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
	        try { if (ps != null) ps.close(); } catch (Exception ignored) {}
	        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
	    }

	    return profile;
	}





	@Override
	public ParentUser fetchUserDetails(String parentId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT " + "u.user_id, u.username, u.password_hash, u.user_type, u.last_login, "
				+ "u.is_active, u.created_date, u.isApproved, "
				+ "p.parent_id, p.parent_name, p.email, p.phone, p.registration_date, "
				+ "c.child_id, c.child_name, c.age, c.gender, c.interests, "
				+ "c.reading_level, c.genres, c.reading_frequency, c.notes " + "FROM parent p "
				+ "JOIN user u ON p.user_id = u.user_id " + "LEFT JOIN child c ON p.parent_id = c.parent_id "
				+ "WHERE p.parent_id = ? " + "ORDER BY c.child_id";

		ParentUser parent = null;

		try {
			conn = DbUtil.getConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, parentId);
			rs = ps.executeQuery();

			while (rs.next()) {

				// =============== Initialize Parent ONLY ONCE ===============
				if (parent == null) {
					parent = new ParentUser();

					// USER DATA
					parent.setUserId(rs.getInt("user_id"));
					

					// PARENT DATA
					parent.setParentId(rs.getInt("parent_id"));
					parent.setParentName(rs.getString("parent_name"));
					parent.setEmail(rs.getString("email"));
					parent.setPhone(rs.getString("phone"));
					parent.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());

					parent.setChildUsers(new ArrayList<>());
				}

				// =============== Add Child (if exists) ===============
				int childId = rs.getInt("child_id");

				if (childId != 0) {
					ChildUser child = new ChildUser();
					child.setChildId(childId);
					child.setParentId(rs.getInt("parent_id"));
					child.setChildName(rs.getString("child_name"));
					child.setAge(rs.getInt("age"));
					child.setGender(rs.getString("gender"));
					child.setInterests(rs.getString("interests"));
					child.setReadingLevel(rs.getString("reading_level"));
					child.setGenres(rs.getString("genres"));
					child.setReadingFrequency(rs.getString("reading_frequency"));
					child.setNotes(rs.getString("notes"));

					parent.getChildUsers().add(child);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return parent; // return ONLY ONE parent object
	}
	
	@Override
	public Transaction approveUserDetails(String parentId) {

	    Connection conn = null;
	    PreparedStatement stmt = null;
	    Transaction transaction = new Transaction();

	    try {
	        conn = DbUtil.getConnection();

	        // STEP 1: Get user_id from parent
	        String getUserSql = "SELECT user_id FROM parent WHERE parent_id = ?";
	        stmt = conn.prepareStatement(getUserSql);
	        stmt.setString(1, parentId);

	        ResultSet rs = stmt.executeQuery();

	        if (!rs.next()) {
	            transaction.setSuccess(false);
	            transaction.setMessage("Parent not found");
	            return transaction;
	        }

	        int userId = rs.getInt("user_id");

	        stmt.close();

	        // STEP 2: Approve user (set isApproved = 1)
	        String updateSql = "UPDATE user SET isApproved = 1 WHERE user_id = ?";
	        stmt = conn.prepareStatement(updateSql);
	        stmt.setInt(1, userId);

	        int updated = stmt.executeUpdate();

	        if (updated > 0) {
	            transaction.setSuccess(true);
	            transaction.setMessage("User approved successfully");
	        } else {
	            transaction.setSuccess(false);
	            transaction.setMessage("Could not update user approval status");
	        }

	    } catch (Exception e) {

	        transaction.setSuccess(false);
	        transaction.setMessage("Error approving user: " + e.getMessage());
	        e.printStackTrace();

	    } finally {
	        try { if (stmt != null) stmt.close(); } catch (Exception ignore) {}
	        try { if (conn != null) conn.close(); } catch (Exception ignore) {}
	    }

	    return transaction;
	}
	
	@Override
	public Transaction updateProfile(
	        int userId,
	        int parentId,
	        int childId,
	        String username,
	        String parentName,
	        String email,
	        String phone,
	        String childName,
	        int age,
	        String gender,
	        String interests,
	        String readingLevel,
	        String genres,
	        String readingFrequency,
	        String notes
	) {

	    Connection conn = null;
	    PreparedStatement psUser = null;
	    PreparedStatement psParent = null;
	    PreparedStatement psChild = null;

	    try {
	        conn = DbUtil.getConnection();
	        conn.setAutoCommit(false); // 🔐 TRANSACTION START

	        /* ================= USER ================= */
	        String userSql =
	            "UPDATE user SET username = ? WHERE user_id = ?";
	        psUser = conn.prepareStatement(userSql);
	        psUser.setString(1, username);
	        psUser.setInt(2, userId);
	        psUser.executeUpdate();

	        /* ================= PARENT ================= */
	        String parentSql =
	            "UPDATE parent SET parent_name = ?, email = ?, phone = ? WHERE parent_id = ?";
	        psParent = conn.prepareStatement(parentSql);
	        psParent.setString(1, parentName);
	        psParent.setString(2, email);
	        psParent.setString(3, phone);
	        psParent.setInt(4, parentId);
	        psParent.executeUpdate();

	        /* ================= CHILD ================= */
	        String childSql =
	            "UPDATE child SET " +
	            "child_name = ?, age = ?, gender = ?, interests = ?, " +
	            "reading_level = ?, genres = ?, reading_frequency = ?, notes = ? " +
	            "WHERE child_id = ?";

	        psChild = conn.prepareStatement(childSql);
	        psChild.setString(1, childName);
	        psChild.setInt(2, age);
	        psChild.setString(3, gender);
	        psChild.setString(4, interests);
	        psChild.setString(5, readingLevel);
	        psChild.setString(6, genres);
	        psChild.setString(7, readingFrequency);
	        psChild.setString(8, notes);
	        psChild.setInt(9, childId);
	        psChild.executeUpdate();

	        conn.commit(); // ✅ SUCCESS
	        
	        Transaction transaction = new Transaction();
	        transaction.setSuccess(true);
	        transaction.setMessage("Your Profile has been updated Successfully");
	        
	        return transaction;
	        
	    } catch (Exception e) {
	        try { if (conn != null) conn.rollback(); } catch (Exception ignored) {}
	        Transaction transaction = new Transaction();
	        transaction.setSuccess(true);
	        transaction.setMessage("Your Profile Update was Unsuccesfull please contact your Admin");
	        
	        return transaction;
	    } finally {
	        try { if (psUser != null) psUser.close(); } catch (Exception ignored) {}
	        try { if (psParent != null) psParent.close(); } catch (Exception ignored) {}
	        try { if (psChild != null) psChild.close(); } catch (Exception ignored) {}
	        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
	    }
	}

	
	@Override
	public Transaction rejectUserDetails(String parentId) {

	    Connection conn = null;
	    PreparedStatement stmt = null;
	    Transaction transaction = new Transaction();

	    try {
	        conn = DbUtil.getConnection();

	        // STEP 1: Get user_id from parent
	        String getUserSql = "SELECT user_id FROM parent WHERE parent_id = ?";
	        stmt = conn.prepareStatement(getUserSql);
	        stmt.setString(1, parentId);

	        ResultSet rs = stmt.executeQuery();

	        if (!rs.next()) {
	            transaction.setSuccess(false);
	            transaction.setMessage("Parent not found");
	            return transaction;
	        }

	        int userId = rs.getInt("user_id");

	        stmt.close();

	        // STEP 2: Approve user (set isApproved = 1)
	        String updateSql = "UPDATE user SET isRejected = 1 WHERE user_id = ?";
	        stmt = conn.prepareStatement(updateSql);
	        stmt.setInt(1, userId);

	        int updated = stmt.executeUpdate();

	        if (updated > 0) {
	            transaction.setSuccess(true);
	            transaction.setMessage("User Record has been Rejected successfully");
	        } else {
	            transaction.setSuccess(false);
	            transaction.setMessage("Could not update user approval status");
	        }

	    } catch (Exception e) {

	        transaction.setSuccess(false);
	        transaction.setMessage("Error approving user: " + e.getMessage());
	        e.printStackTrace();

	    } finally {
	        try { if (stmt != null) stmt.close(); } catch (Exception ignore) {}
	        try { if (conn != null) conn.close(); } catch (Exception ignore) {}
	    }

	    return transaction;
	}
	
	
	
	
	
	
}
