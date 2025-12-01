package com.Bookalay.dao;

import java.util.List;

import com.Bookalay.pojo.Request;


public interface RequestDao {
	public List<Request> fetchActiveRequests();
	public List<Request> fetchRequestsDetailsById(String id);
}
