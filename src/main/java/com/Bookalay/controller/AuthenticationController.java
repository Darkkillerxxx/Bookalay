package com.Bookalay.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bookalay.pojo.User;
import com.Bookalay.service.UserService;
import com.Bookalay.serviceImpl.UserServiceImpl;

/**
 * Servlet implementation class AuthenticationController
 */
@WebServlet("/AuthenticationController")
public class AuthenticationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthenticationController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("authenticate".equalsIgnoreCase(action)) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			UserService userService = new UserServiceImpl();
			User user = userService.checkLogin(username, password);
			
			if(user != null) {
				System.out.print("User Found");
				System.out.print(user.getUserType());
				if(user.getUserType().equalsIgnoreCase("admin")) {
					RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/adminDashboard.jsp");
					dispatcher.forward(request, response);
				}
				else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/parentDashboard.jsp");
					dispatcher.forward(request, response);
				}
			}
			else {
				request.setAttribute("errorMessage","Invalid Username Or Password");
				RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/login.jsp");
				dispatcher.forward(request, response);
			}
		}else {
			System.out.print("No Action Found");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
