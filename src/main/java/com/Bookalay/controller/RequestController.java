package com.Bookalay.controller;

import java.io.IOException;
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
		com.Bookalay.pojo.User user = (com.Bookalay.pojo.User) request.getSession().getAttribute("user");
		
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
				 // Get user from session
			    if (user == null) {
			        response.sendRedirect("login.jsp");
			        return;
			    }

			    int userId = user.getUserId();

			    // Read form fields
			    String bookId = request.getParameter("book_id");
			    String notes = request.getParameter("notes");

			    java.util.Date requestDate = new java.util.Date(); // current timestamp

			    RequestService reqService = new RequestServiceImpl();
			    Transaction t = reqService.raiseRequest(userId, bookId, requestDate, notes);

			    request.setAttribute("transaction", t);

			    RequestDispatcher rd = request.getRequestDispatcher("jsp/raiseRequest.jsp");
			    rd.forward(request, response);

				break;
				
			case "viewRequestParent":
			    int loggedInUserId = user.getUserId();
			    RequestService reqParentService = new RequestServiceImpl();
			    
			    List<Request> requestDetailList = reqParentService.fetchRequestsDetailsById(loggedInUserId);
				request.setAttribute("requestList", requestDetailList);
			    
				RequestDispatcher viewRequestParentDispatcher = request.getRequestDispatcher("jsp/requestsParent.jsp");
				viewRequestParentDispatcher.forward(request, response);

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
