package com.Bookalay.serviceImpl;

import java.util.List;

import com.Bookalay.dao.UserDao;
import com.Bookalay.daoImpl.UserDaoImpl;
import com.Bookalay.pojo.ParentUser;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.pojo.User;
import com.Bookalay.pojo.UserProfile;
import com.Bookalay.service.UserService;

public class UserServiceImpl implements UserService {
	
	UserDao userDao = new UserDaoImpl();
	
	@Override
	public User checkLogin(String Username, String Password) {
		// TODO Auto-generated method stub
		return userDao.checkLogin(Username, Password);
	}

	@Override
	public Transaction insertUser(String Username, String Password, String childName, String age, String gender,String interests, String parentName, String phoneNumber, String email, String readingLevel, String genres,String readingFrequency, String notes) {
		return userDao.insertUser(Username, Password, childName, age, gender, interests, parentName, phoneNumber, email, readingLevel, genres, readingFrequency, notes);
	}

	@Override
	public List<ParentUser> fetchParentUsersForApproval(String searchText) {
		// TODO Auto-generated method stub
		return userDao.fetchParentUsersForApproval(searchText);
	}

	@Override
	public ParentUser fetchUserDetails(String parentId) {
		// TODO Auto-generated method stub
		return userDao.fetchUserDetails(parentId);
	}

	@Override
	public Transaction approveUserDetails(String parentId) {
		// TODO Auto-generated method stub
		return userDao.approveUserDetails(parentId);
	}
	
	@Override
	public UserProfile fetchUserProfile(String parentId) {
		// TODO Auto-generated method stub
		return userDao.fetchUserProfile(parentId);
	}

	@Override
	public Transaction rejectUserDetails(String parentId) {
		return userDao.rejectUserDetails(parentId);
	}

	@Override
	public List<ParentUser> fetchAllUser(String searchText) {
		return userDao.fetchAllUsers(searchText);
	}

	@Override
	public Transaction updateProfile(int userId, int parentId, int childId, String username, String parentName,
			String email, String phone, String childName, int age, String gender, String interests, String readingLevel,
			String genres, String readingFrequency, String notes) {
		
		return userDao.updateProfile(userId, parentId, childId, username, parentName, email, phone, childName, age, gender, interests, readingLevel, genres, readingFrequency, notes);
	}

	@Override
	public Transaction activateOrDeactivateUser(String parentId, Boolean isActive) {
		// TODO Auto-generated method stub
		return userDao.activateOrDeactivateUser(parentId, isActive);
	}
}
