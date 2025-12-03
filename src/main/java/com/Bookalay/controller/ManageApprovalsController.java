package com.Bookalay.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bookalay.pojo.ParentUser;
import com.Bookalay.pojo.Transaction;
import com.Bookalay.service.UserService;
import com.Bookalay.serviceImpl.UserServiceImpl;

/**
 * Servlet implementation class ManageApprovals
 */
@WebServlet("/ManageApprovalsController")
public class ManageApprovalsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageApprovalsController() {
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
        UserService userService = new UserServiceImpl();

	    switch (action) {

	        case "manageApprovals":
	            // Get search keyword from request
	            String search = request.getParameter("search");
	            if (search == null) {
	                search = "";
	            }
	            
	            // Pass search to service
	            List<ParentUser> fetchParentUsersForApproval =
	                    userService.fetchParentUsersForApproval(search);

	            request.setAttribute("usersForApproval", fetchParentUsersForApproval);
	            request.setAttribute("search", search); // to refill search box in JSP

	            RequestDispatcher dispatcher =request.getRequestDispatcher("jsp/manageApprovals.jsp");
	            dispatcher.forward(request, response);

	            break;
	            
	        case "viewApproverDetail":
	            String parentId = request.getParameter("userId"); // <-- FIXED
	            System.out.println("userId --> " + parentId);

	            ParentUser parentUser = userService.fetchUserDetails(parentId);
	            System.out.println(parentUser);

	            // *** PASS OBJECT TO JSP ***
	            request.setAttribute("parent", parentUser);

	            RequestDispatcher approverDetailDispatcher =
	                    request.getRequestDispatcher("jsp/approverDetail.jsp");
	            approverDetailDispatcher.forward(request, response);

	            break;
	        
	        case "approveUser":

	            String approveParentId = request.getParameter("userId");
	            System.out.println("Approve ParentId --> " + approveParentId);

	            Transaction transaction = userService.approveUserDetails(approveParentId);
	            
	            ParentUser approvedParentUser = userService.fetchUserDetails(approveParentId);
	            System.out.println(approvedParentUser);

	            // *** PASS OBJECT TO JSP ***
	            request.setAttribute("parent", approvedParentUser);

	            request.setAttribute("userId", approveParentId);
	            request.setAttribute("showToast", transaction.isSuccess());
	            request.setAttribute("toastMessage", transaction.getMessage());

	            request.getRequestDispatcher("jsp/approverDetail.jsp")
	                   .forward(request, response);

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
