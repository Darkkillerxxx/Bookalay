package com.Bookalay.service;

import java.util.List;

import com.Bookalay.pojo.ParentUser;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.pojo.User;

public interface UserService {

	public User checkLogin(String Username,String Password);
	public Transaction insertUser(String Username, String Password, String childName, String age, String gender,String interests,String parentName,String phoneNumber,String email,String readingLevel,String genres,String readingFrequency,String notes);
	public List<ParentUser> fetchParentUsersForApproval(String searchText);
	public ParentUser fetchUserDetails(String parentId);
	public Transaction approveUserDetails(String parentId);
}
