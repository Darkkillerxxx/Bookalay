package com.Bookalay.serviceImpl;

import java.util.List;

import com.Bookalay.dao.BookDao;
import com.Bookalay.daoImpl.BookDaoImpl;
import com.Bookalay.pojo.Book;
import com.Bookalay.service.BookService;

public class BookServiceImpl implements BookService {

	BookDao bookDao = new BookDaoImpl();

	@Override
	public List<Book> fetchAvailableBooks(String search, String interests, String genres) {
		// TODO Auto-generated method stub
		List<Book> fetchedBookList =  bookDao.fetchAvailableBooks(search,interests,genres);
		
		return fetchedBookList;
	}
	
}
