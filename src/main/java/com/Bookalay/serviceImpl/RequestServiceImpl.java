package com.Bookalay.serviceImpl;

import java.util.List;

import com.Bookalay.dao.RequestDao;
import com.Bookalay.daoImpl.RequestDaoImpl;
import com.Bookalay.pojo.Request;
import com.Bookalay.service.RequestService;

public class RequestServiceImpl implements RequestService{
	
	RequestDao requestDao = new RequestDaoImpl();
	
	@Override
	public List<Request> fetchActiveRequests() {
		return requestDao.fetchActiveRequests();
	}

}
