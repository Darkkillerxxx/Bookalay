package com.Bookalay.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.Bookalay.dao.UserDao;
import com.Bookalay.pojo.User;
import com.Bookalay.util.DbUtil;

public class UserDaoImpl implements UserDao {

	@Override
	public User checkLogin(String Username, String Password) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		
		String query = "SELECT * FROM users WHERE username = ? AND password_hash = ?";
		
		try {
			conn = DbUtil.getConnection();
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, Username);
			preparedStatement.setString(2, Password);
			
			rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
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
		}
		finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(preparedStatement != null) {
					preparedStatement.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return null;
	}

	@Override
	public void insertUser(String Username, String Password, String childName, String age, String gender,
	        String interests, String parentName, String phoneNumber, String email, String readingLevel,
	        String genres, String readingFrequency, String notes) {

	    Connection conn = null;
	    PreparedStatement psUser = null;
	    PreparedStatement psParent = null;
	    PreparedStatement psChild = null;
	    ResultSet rsUser = null;
	    ResultSet rsParent = null;

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
	            if (rsUser.next()) {
	                userId = rsUser.getInt(1);
	            }
	        }

	        // ❌ If userId not generated → STOP
	        if (userId == 0) {
	            System.out.println("User insert failed. Parent & Child NOT inserted.");
	            return;
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
	            if (rsParent.next()) {
	                parentId = rsParent.getInt(1);
	            }
	        }

	        // ❌ If parentId not generated → STOP
	        if (parentId == 0) {
	            System.out.println("Parent insert failed. Child NOT inserted.");
	            return;
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

	        System.out.println("User, Parent, Child inserted successfully.");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rsUser != null) rsUser.close();
	            if (rsParent != null) rsParent.close();
	            if (psChild != null) psChild.close();
	            if (psParent != null) psParent.close();
	            if (psUser != null) psUser.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
