package com.Bookalay.service;

import java.util.Date;
import java.util.List;

import com.Bookalay.pojo.Request;
import com.Bookalay.pojo.Transaction;

public interface RequestService {
	public List<Request> fetchActiveRequests(boolean isAdmin,String searchText);
	public List<Request> fetchRequestsDetailsById(int id);
	public Transaction raiseRequest(int userId, String bookId, Date requestDate, String additionalNotes);
	public List<Request> fetchRequestsForBookId(String bookId);
	public Request fetchRequestDetails(int id);
	public Transaction approveRequest(int requestId);
	public Transaction issueRequest(int requestId);
	public Transaction rejectRequest(int requestId,String notes);
	public Transaction returnRequest(int requestId);
}
