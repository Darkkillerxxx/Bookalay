package com.Bookalay.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bookalay.daoImpl.BookDaoImpl;
import com.Bookalay.pojo.Book;
import com.Bookalay.pojo.Request;
import com.Bookalay.service.DashboardService;
import com.Bookalay.serviceImpl.DashboardServiceImpl;

/**
 * Servlet implementation class DashboardController
 */
@WebServlet("/DashboardController")
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action").trim();
		System.out.print(action);
		switch (action) {
		
		case "viewBooksDetails":
			String bookIdStr = request.getParameter("bookId");
			if (bookIdStr != null) {
				int bookId = Integer.parseInt(bookIdStr);
				BookDaoImpl bookDao = new BookDaoImpl();
				Book book = bookDao.getBookById(bookId);

				request.setAttribute("book", book);
				RequestDispatcher bookDetailsDispatcher = request.getRequestDispatcher("jsp/viewBookDetail.jsp");
				bookDetailsDispatcher.forward(request, response);
			}
			break;

		case "home":
			com.Bookalay.pojo.User loggedInUser = (com.Bookalay.pojo.User) request.getSession().getAttribute("user");
			DashboardService dashboardService = new DashboardServiceImpl();

			if ("admin".equalsIgnoreCase(loggedInUser.getUserType())) {
				System.out.print(dashboardService.getTotalUsersForAdmin());
				request.setAttribute("totalUsers", dashboardService.getTotalUsersForAdmin());
				request.setAttribute("booksIssued", dashboardService.getTotalBooksIssuedForAdmin());
				request.setAttribute("booksRead", dashboardService.getTotalBooksReadForAdmin());
				request.setAttribute("usersWaitingForApproval", dashboardService.getUsersWaitingForApproval());
				request.setAttribute("inactiveUsers", dashboardService.getInactiveUsersForAdmin());
				request.setAttribute("overdueBooks", dashboardService.getOverdueBooksForAdmin());
				request.setAttribute("totalBooksAvailable", dashboardService.getTotalBooksAvailableForAdmin());
				request.setAttribute("newBooksRequestedToday", dashboardService.getNewBooksRequestedToday());
				
				RequestDispatcher adminDispatcher = request.getRequestDispatcher("jsp/adminDashboard.jsp");
				adminDispatcher.forward(request, response);
			} else {
				// In real app, get parentId from logged-in user/session
				String parentIdParam = request.getParameter("parentId");
				int parentId = (parentIdParam != null) ? Integer.parseInt(parentIdParam) : 1;

				// 1) Top metrics
				int totalRequests = dashboardService.getTotalRequests(parentId);
				int booksIssued = dashboardService.getBooksCurrentlyIssued(parentId);
				int overdueCount = dashboardService.getOverdueBooksCount(parentId);
				int dueSoonCount = dashboardService.getUpcomingDue(parentId, 3).size();
				int booksReturned = dashboardService.getBooksReturned(parentId);
				
				List<Request> upcommingDueRequest = dashboardService.getUpcomingDueRequestForAdmin(7);
				
				Map<String, Integer> byStatus = dashboardService.getCountsByStatus(parentId);

				request.setAttribute("totalRequests", totalRequests);
				request.setAttribute("booksIssued", booksIssued);
				request.setAttribute("overdueCount", overdueCount);
				request.setAttribute("dueSoonCount", dueSoonCount);
				request.setAttribute("booksReturned", booksReturned);
				request.setAttribute("statusCounts", byStatus);
				request.setAttribute("upcommingDueRequest",upcommingDueRequest);

				// 2) Tables
				List<Request> recentRequests = dashboardService.getRecentRequests(parentId, true);
				List<Request> issuedBooks = dashboardService.getIssuedBooks(parentId, true);
				List<Request> overdueBooks = dashboardService.getOverdueBooks(parentId);
				List<Request> returnedBooks = dashboardService.getReturnedBooks(parentId);
				List<Request> upcomingDue = dashboardService.getUpcomingDue(parentId, 7);

				request.setAttribute("recentRequests", recentRequests);
				request.setAttribute("issuedBooks", issuedBooks);
				request.setAttribute("overdueBooks", overdueBooks);
				request.setAttribute("returnedBooks", returnedBooks);
				request.setAttribute("upcomingDue", upcomingDue);

				RequestDispatcher parentDispatcher = request.getRequestDispatcher("jsp/parentDashboard.jsp");
				parentDispatcher.forward(request, response);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
