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
	public List<Request> fetchActiveRequests() {
		return requestDao.fetchActiveRequests();
	}
	
	@Override
	public Transaction raiseRequest(int userId, String bookId, Date requestDate, String additionalNotes){
		return requestDao.raiseRequest(userId, bookId, requestDate, additionalNotes);
	}

	@Override
	public List<Request> fetchRequestsDetailsById(int id) {
		return requestDao.fetchRequestsDetailsById(id);
	}

}
