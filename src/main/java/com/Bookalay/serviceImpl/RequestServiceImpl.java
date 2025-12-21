package com.Bookalay.serviceImpl;

import java.util.Date;
import java.util.List;

import com.Bookalay.dao.RequestDao;
import com.Bookalay.daoImpl.RequestDaoImpl;
import com.Bookalay.pojo.Request;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.service.RequestService;

public class RequestServiceImpl implements RequestService{
	
	RequestDao requestDao = new RequestDaoImpl();
	
	@Override
	public List<Request> fetchActiveRequests(boolean isAdmin,String searchText) {
		return requestDao.fetchActiveRequests(isAdmin,searchText);
	}
	
	@Override
	public Transaction raiseRequest(int userId, String bookId, Date requestDate, String additionalNotes){
		return requestDao.raiseRequest(userId, bookId, requestDate, additionalNotes);
	}

	@Override
	public List<Request> fetchRequestsDetailsById(int id) {
		return requestDao.fetchRequestsDetailsById(id);
	}

	@Override
	public List<Request> fetchRequestsForBookId(String bookId) {
		// TODO Auto-generated method stub
		return requestDao.fetchRequestsForBookId(bookId);
	}

	@Override
	public Request fetchRequestDetails(int id) {
		return requestDao.fetchRequestDetails(id);
	}

	@Override
	public Transaction approveRequest(int requestId) {
		return requestDao.approveRequest(requestId);
	}

	@Override
	public Transaction issueRequest(int requestId) {
		return requestDao.issueRequest(requestId);
	}

	@Override
	public Transaction rejectRequest(int requestId,String notes) {
		return requestDao.rejectRequest(requestId,notes);
	}

	@Override
	public Transaction returnRequest(int requestId) {
		return requestDao.returnRequest(requestId);
	}
}
