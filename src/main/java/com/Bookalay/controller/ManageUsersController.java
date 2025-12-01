package com.Bookalay.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Bookalay.pojo.User;
import com.Bookalay.pojo.UserProfile;
import com.Bookalay.service.UserService;
import com.Bookalay.serviceImpl.UserServiceImpl;

/**
 * Servlet implementation class ManageUsers
 */
@WebServlet("/ManageUsersController")
public class ManageUsersController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageUsersController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User loggedInUser = null;

		if (session != null) {
			loggedInUser = (User) session.getAttribute("user");
		}

		// 🔒 If not logged in → send to login page
		if (loggedInUser == null) {
			response.sendRedirect("jsp/login.jsp");
			return;
		}

		String action = request.getParameter("action");

		switch (action) {
		case "viewProfile":

			// get parentId from session user
			String parentId = String.valueOf(loggedInUser.getUserId());

			UserService service = new UserServiceImpl();
			UserProfile profile = service.fetchUserProfile(parentId);

			request.setAttribute("profile", profile);

			RequestDispatcher rd = request.getRequestDispatcher("jsp/viewProfile.jsp");
			rd.forward(request, response);
			break;

		default:
			System.out.println("Unknown action");
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
