package com.Bookalay.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bookalay.pojo.Request;
import com.Bookalay.service.RequestService;
import com.Bookalay.serviceImpl.RequestServiceImpl;

/**
 * Servlet implementation class RequestController
 */
@WebServlet("/RequestController")
public class RequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action").trim();
		System.out.print(action);
		switch (action) {
			case "viewRequest":
				
				RequestService requestService = new RequestServiceImpl();
				List<Request> reqList = requestService.fetchActiveRequests();
				
		        System.out.println(reqList);
				request.setAttribute("requestList", reqList);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/requests.jsp");
				dispatcher.forward(request, response);
				
				break;
			
			case "requestDetails":
				RequestDispatcher requestDetailDispatcher = request.getRequestDispatcher("jsp/requestDetails.jsp");
				requestDetailDispatcher.forward(request, response);
				break;
			default:
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
