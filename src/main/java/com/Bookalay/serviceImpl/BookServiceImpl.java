package com.Bookalay.serviceImpl;

import java.util.List;

import com.Bookalay.dao.BookDao;
import com.Bookalay.daoImpl.BookDaoImpl;
import com.Bookalay.pojo.Book;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.service.BookService;

public class BookServiceImpl implements BookService {

	BookDao bookDao = new BookDaoImpl();

	@Override
	public List<Book> fetchAvailableBooks(String search, String interests, String genres,boolean isAdmin) {
		List<Book> fetchedBookList =  bookDao.fetchAvailableBooks(search,interests,genres,isAdmin);
		return fetchedBookList;
	}

	@Override
	public Book getBookById(int bookId) {
		return bookDao.getBookById(bookId);
	}

	@Override
	public Transaction createBook(Book book) {
		return bookDao.createBook(book);
	}

	@Override
	public Transaction toggleBookAvailability(int bookId) {
		// TODO Auto-generated method stub
		return bookDao.toggleBookAvailability(bookId);
	}

	@Override
	public List<Book> recommendBooksForParent(int parentId) {
		// TODO Auto-generated method stub
		return bookDao.recommendBooksForParent(parentId);
	}
	
}
