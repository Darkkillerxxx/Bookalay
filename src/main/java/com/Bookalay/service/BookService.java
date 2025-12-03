package com.Bookalay.service;

import java.util.List;

import com.Bookalay.pojo.Book;

public interface BookService {
	public List<Book> fetchAvailableBooks(String search, String interests, String genres);
	public Book getBookById(int bookId);
}
