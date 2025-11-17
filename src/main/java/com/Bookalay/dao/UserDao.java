package com.Bookalay.dao;

import com.Bookalay.pojo.Transaction;
import com.Bookalay.pojo.User;

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
}
