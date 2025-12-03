package com.Bookalay.service;

import java.util.Date;
import java.util.List;

import com.Bookalay.pojo.Request;
import com.Bookalay.pojo.Transaction;

public interface RequestService {
	public List<Request> fetchActiveRequests();
	public List<Request> fetchRequestsDetailsById(int id);
	public Transaction raiseRequest(int userId, String bookId, Date requestDate, String additionalNotes);
	
}
