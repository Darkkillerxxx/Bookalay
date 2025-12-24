package com.Bookalay.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bookalay.pojo.ParentDashboardData;
import com.Bookalay.pojo.ParentUser;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.pojo.User;
import com.Bookalay.pojo.UserProfile;
import com.Bookalay.service.DashboardService;
import com.Bookalay.service.UserService;
import com.Bookalay.serviceImpl.DashboardServiceImpl;
import com.Bookalay.serviceImpl.UserServiceImpl;
import com.Bookalay.util.UserUtil;

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

		String parentId;
		UserService userService = new UserServiceImpl();
		ParentUser parentUser = new ParentUser();
		User loggedInUser = UserUtil.fetchLoggedInUser(request, response);

		// 🔒 If not logged in → send to login page
		if (loggedInUser == null) {
			response.sendRedirect("/");
			return;
		}

		String action = request.getParameter("action");

		switch (action) {
		case "viewProfile":

			parentId = String.valueOf(loggedInUser.getUserId());

			UserProfile profile = userService.fetchUserProfile(parentId);
			request.setAttribute("profile", profile);

			RequestDispatcher rd = request.getRequestDispatcher("jsp/viewProfile.jsp");
			rd.forward(request, response);
			break;

		case "updateProfile":

			int userId = Integer.parseInt(request.getParameter("userId"));
			int parentIdInt = Integer.parseInt(request.getParameter("parentId"));
			int childId = Integer.parseInt(request.getParameter("childId"));

			// USER
			String username = request.getParameter("username");

			// PARENT
			String parentName = request.getParameter("parentName");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");

			// CHILD
			String childName = request.getParameter("childName");
			int age = Integer.parseInt(request.getParameter("age"));
			String gender = request.getParameter("gender");

			String interests = request.getParameterValues("interests") != null
					? String.join(",", request.getParameterValues("interests"))
					: "";

			String genres = request.getParameterValues("genres") != null
					? String.join(",", request.getParameterValues("genres"))
					: "";

			String readingLevel = request.getParameter("readingLevel");
			String readingFrequency = request.getParameter("readingFrequency");
			String notes = request.getParameter("notes");

			Transaction transaction = userService.updateProfile(userId, parentIdInt, childId, username, parentName,
					email, phone, childName, age, gender, interests, readingLevel, genres, readingFrequency, notes);

			request.setAttribute("transaction", transaction);
			request.setAttribute("showToast", true);

			RequestDispatcher updateProfileDispatcher = request
					.getRequestDispatcher("ManageUsersController?action=viewProfile");
			updateProfileDispatcher.forward(request, response);

			break;

		case "fetchAllUsers":
			String searchText = request.getParameter("search");

			if (searchText == null) {
				searchText = "";
			}
			System.out.println(searchText);

			List<ParentUser> parentUserList = userService.fetchAllUser(searchText);

			request.setAttribute("parentUserList", parentUserList);

			RequestDispatcher allUsersRequestDispatcher = request.getRequestDispatcher("jsp/viewAllUsers.jsp");
			allUsersRequestDispatcher.forward(request, response);
			break;

		case "viewUserDetails":
			parentId = request.getParameter("userId");
			System.out.println("userId --> " + parentId);

			parentUser = userService.fetchUserDetails(parentId);
			System.out.println(parentUser);

			// *** PASS OBJECT TO JSP ***
			request.setAttribute("parent", parentUser);
			fetchParentMetricsForUserDetails(request, response, parentId);
			break;

		case "activateDeactivateUser":
			parentId = request.getParameter("userId");
			boolean isActive = Boolean.valueOf(request.getParameter("isActive"));
			Transaction t = userService.activateOrDeactivateUser(parentId, isActive);
			request.setAttribute("showToast",true);

			request.setAttribute("toastMessage", t.getMessage());

			RequestDispatcher activateDeactivateDispatcher = request
					.getRequestDispatcher("ManageUsersController?action=viewUserDetails");
			activateDeactivateDispatcher.forward(request, response);
			break;

		default:
			System.out.println("Unknown action");
			break;
		}
	}

	private static void fetchParentMetricsForUserDetails(HttpServletRequest request, HttpServletResponse response,
			String parentId) throws ServletException, IOException {

		DashboardService dashboardService = new DashboardServiceImpl();
		ParentDashboardData pdd = new ParentDashboardData();

		pdd.setRecentBookRequests(dashboardService.getRecentRequests(Integer.valueOf(parentId), false));
		pdd.setBooksCurrentlyIssuedList(dashboardService.getIssuedBooks(Integer.valueOf(parentId), false));
		pdd.setLateReturns(dashboardService.getReturnedLateRequests(Integer.valueOf(parentId)));

		request.setAttribute("dashboardMetrics", pdd);

		RequestDispatcher viewUserDetailsDispatcher = request.getRequestDispatcher("jsp/userDetails.jsp");
		viewUserDetailsDispatcher.forward(request, response);
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
