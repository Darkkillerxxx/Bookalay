package com.Bookalay.service;

import java.util.List;

import com.Bookalay.pojo.Request;

public interface RequestService {
	public List<Request> fetchActiveRequests();
}
