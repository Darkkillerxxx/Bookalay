package com.Bookalay.dao;

import java.util.List;

import com.Bookalay.pojo.Book;
import com.Bookalay.pojo.Transaction;

public interface BookDao {
	
	public List<Book> fetchAvailableBooks(String search, String interests, String genres, boolean isAdmin);
	public Book getBookById(int bookId);
	public Transaction createBook(Book book);
	public Transaction toggleBookAvailability(int bookId);
	public List<Book> recommendBooksForParent(int parentId);
	public Transaction editBook(Book book);
}
