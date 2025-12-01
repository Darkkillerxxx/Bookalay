package com.Bookalay.dao;

import java.util.List;

import com.Bookalay.pojo.ParentUser;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.pojo.User;
import com.Bookalay.pojo.UserProfile;

public interface UserDao {
	
	public User checkLogin(String Username,String Password);
	public Transaction insertUser(
			String Username, 
			String Password, 
			String childName, 
			String age, 
			String gender,
			String interests,
			String parentName,
			String phoneNumber,
			String email,
			String readingLevel,
			String genres,
			String readingFrequency,
			String notes
			);
	public List<ParentUser> fetchParentUsersForApproval(String search);
	public ParentUser fetchUserDetails(String userId);
	public Transaction approveUserDetails(String parentId);
	public List<ParentUser> fetchAllUsers(String search);
	public List<ParentUser> activateOrDeactivateUser(String parentId, Boolean isActive);
	public UserProfile fetchUserProfile(String parentId);
}
