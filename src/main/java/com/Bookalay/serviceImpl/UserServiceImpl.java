package com.Bookalay.serviceImpl;

import com.Bookalay.dao.UserDao;
import com.Bookalay.daoImpl.UserDaoImpl;
import com.Bookalay.pojo.User;
import com.Bookalay.service.UserService;

public class UserServiceImpl implements UserService {
	
	UserDao userDao = new UserDaoImpl();
	
	@Override
	public User checkLogin(String Username, String Password) {
		// TODO Auto-generated method stub
		return userDao.checkLogin(Username, Password);
	}

	@Override
	public void insertUser(String Username, String Password, String childName, String age, String gender,
			String interests, String parentName, String phoneNumber, String email, String readingLevel, String genres,
			String readingFrequency, String notes) {
		
		userDao.insertUser(Username, Password, childName, age, gender, interests, parentName, phoneNumber, email, readingLevel, genres, readingFrequency, notes);
	}

}
