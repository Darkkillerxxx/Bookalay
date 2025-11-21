package com.Bookalay.dao;

import java.util.List;

import com.Bookalay.pojo.Book;

public interface BookDao {
	
	public List<Book> fetchAvailableBooks(String search, String interests, String genres);
}
