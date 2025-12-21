package com.Bookalay.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bookalay.daoImpl.BookDaoImpl;
import com.Bookalay.pojo.Book;
import com.Bookalay.pojo.Request;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.pojo.User;
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
		User user = (com.Bookalay.pojo.User) request.getSession().getAttribute("user");
		String userType = user.getUserType();
		RequestService requestService = new RequestServiceImpl();

		switch (action) {
			case "viewRequest":				
				List<Request> reqList = new ArrayList<>();
				String searchText = request.getParameter("searchText");

				System.out.print("userType-->"+userType);
				
				if(userType.equals("admin")) {
					reqList = requestService.fetchActiveRequests(true,searchText);
				}
				else {
					reqList = requestService.fetchActiveRequests(false,searchText);
				}
				
		        System.out.println(reqList);
				request.setAttribute("requestList", reqList);
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/requests.jsp");
				dispatcher.forward(request, response);
				
				break;
			
			case "requestDetails":
				getRequestDetails(request,response,null);
				break;
			
			case "requestDetailsParent":
				getRequestDetailsForParent(request,response);
				break;
			
			case "showRequestForm":
				String bookIdStr = request.getParameter("bookId");
				if (bookIdStr != null) {
				    int bookId = Integer.parseInt(bookIdStr);
				    BookDaoImpl bookDao = new BookDaoImpl();
				    Book book = bookDao.getBookById(bookId);
				    
				    request.setAttribute("book", book);
				}
				
				RequestDispatcher raiseRequestDispatcher = request.getRequestDispatcher("jsp/raiseRequest.jsp");
				raiseRequestDispatcher.forward(request, response);
				break;
			
			case "raiseRequest":
				int userId = user.getUserId();

			    // Read form fields
			    String bookId = request.getParameter("book_id");
			    String notes = request.getParameter("notes");

			    String requestDateString = request.getParameter("request_date");
			    java.sql.Date requestDate = null;
			    if (requestDateString != null && !requestDateString.isEmpty()) {
			        requestDate = java.sql.Date.valueOf(requestDateString);
			    }

			    Transaction t = requestService.raiseRequest(userId, bookId, requestDate, notes);

			    request.setAttribute("toastMessage", t.getMessage());
			    request.setAttribute("showToast",true);

			    RequestDispatcher rd = request.getRequestDispatcher("RequestController?action=viewRequestParent");
			    rd.forward(request, response);

				break;
				
			case "viewRequestParent":
			    int loggedInUserId = user.getUserId();
			    System.out.print(loggedInUserId);
			    List<Request> requestDetailList = requestService.fetchRequestsDetailsById(loggedInUserId);
				request.setAttribute("requestList", requestDetailList);
			    
				RequestDispatcher viewRequestParentDispatcher = request.getRequestDispatcher("jsp/requestsParent.jsp");
				viewRequestParentDispatcher.forward(request, response);

				break;
			
			case "rejectRequest":
				int requestId = Integer.parseInt(request.getParameter("requestId"));
				String notesForRejection = request.getParameter("notes");

			    Transaction rejectTransaction = requestService.rejectRequest(requestId,notesForRejection);
			    
				getRequestDetails(request,response,rejectTransaction);
				break;
				
			case "issueRequest":
				int requestIdForIssue = Integer.parseInt(request.getParameter("requestId"));

			    Transaction issueTransaction = requestService.issueRequest(requestIdForIssue);
			    
			    getRequestDetails(request,response,issueTransaction);
				break;
				
			case "returnRequest":
				int requestIdForReturn = Integer.parseInt(request.getParameter("requestId"));

			    Transaction returnTransaction = requestService.returnRequest(requestIdForReturn);
			    
			    getRequestDetails(request,response,returnTransaction);
				break;
			
				
			case "approveRequest":
				int requestIdForApproval = Integer.parseInt(request.getParameter("requestId"));

			    Transaction approvalTransaction = requestService.approveRequest(requestIdForApproval);
			    
			    getRequestDetails(request,response,approvalTransaction);
				break;
				
			default:
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void getRequestDetails(HttpServletRequest request, HttpServletResponse response, Transaction transaction) throws ServletException, IOException {
		RequestService requestService = new RequestServiceImpl();
		
		String bookIdString = request.getParameter("bookId");
		String requestId = request.getParameter("requestId");
		
		System.out.print(163);		
		System.out.print(bookIdString);
		
		List<Request> requestListForBookId = requestService.fetchRequestsForBookId(bookIdString);
		Request requestDetail = requestService.fetchRequestDetails(Integer.valueOf(requestId));
		
		request.setAttribute("requestList", requestListForBookId);
		request.setAttribute("requestDetail", requestDetail);
		
		if(transaction != null) {
			request.setAttribute("showToast", true);
		    request.setAttribute("transaction", transaction);
		}
		
		RequestDispatcher requestDetailDispatcher = request.getRequestDispatcher("jsp/requestDetails.jsp");
		requestDetailDispatcher.forward(request, response);
	}
	
	protected void getRequestDetailsForParent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestService requestService = new RequestServiceImpl();
		
		String requestId = request.getParameter("requestId");
		
		System.out.print(163);		
		
		Request requestDetail = requestService.fetchRequestDetails(Integer.valueOf(requestId));
		
		request.setAttribute("requestDetail", requestDetail);
		
		RequestDispatcher requestDetailDispatcher = request.getRequestDispatcher("jsp/requestDetailsParent.jsp");
		requestDetailDispatcher.forward(request, response);
	}
	
	

}
