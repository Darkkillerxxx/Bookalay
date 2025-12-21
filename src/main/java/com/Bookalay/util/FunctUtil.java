package com.Bookalay.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FunctUtil {
	public static Integer getParentIdByUserId(int userId) {

	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = DbUtil.getConnection();
	        ps = conn.prepareStatement(
	            "SELECT parent_id FROM parent WHERE user_id = ?"
	        );
	        ps.setInt(1, userId);

	        rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getInt("parent_id");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
	        try { if (ps != null) ps.close(); } catch (Exception ignored) {}
	        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
	    }

	    return null;
	}

}
