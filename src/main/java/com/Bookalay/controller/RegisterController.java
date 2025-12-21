package com.Bookalay.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bookalay.pojo.Transaction;
import com.Bookalay.service.UserService;
import com.Bookalay.serviceImpl.UserServiceImpl;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		if("registerNewUser".equalsIgnoreCase(action)) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/register.jsp");
			dispatcher.forward(request, response);
		}
		else if ("register".equalsIgnoreCase(action)) {

		    // Child info
		    String childName = request.getParameter("childName");
		    String age = request.getParameter("age");
		    String gender = request.getParameter("gender");
		    String readingLevel = request.getParameter("readingLevel");
		    String interests = request.getParameterValues("interests") != null ? String.join(",", request.getParameterValues("interests")) : ""; 
		    
		    // Parent info
		    String parentName = request.getParameter("parentName");
		    String phone = request.getParameter("phone");
		    String email = request.getParameter("email");

		    // Login setup
		    String username = request.getParameter("username");
		    String password = request.getParameter("password");
		    String reEnteredPassword = request.getParameter("reEnteredPassword");

		    // Reading preferences
		    String genres = request.getParameterValues("genres") != null ? String.join(",", request.getParameterValues("genres")) : "";   // FIX name in JSP!
		    String readingFrequency = request.getParameter("readingFrequency");
		    String notes = request.getParameter("notes");
		    
		    Boolean hasValidationError = formValidator(request,response,interests,username, password,reEnteredPassword, genres);
		    
		    if(!hasValidationError) {
				UserService userService = new UserServiceImpl();
				
				Transaction transaction = userService.insertUser(username, password, childName, age, gender, interests, parentName, phone, email, readingLevel, genres, readingFrequency, notes);
				request.setAttribute("showToast", true);
				request.setAttribute("toastMessage", transaction.getMessage());
				request.getRequestDispatcher("/").forward(request, response);
				
				System.out.print(transaction.getMessage());
		    }
		   
			// Now you can save this to DB or process it
		}
	}
	
	private boolean formValidator(HttpServletRequest request,HttpServletResponse response,String interests,String username, String password, String reEnteredPassword, String genres) { 
		 boolean hasValidationError = false;
		  
		  if(username.length() == 0 || username == null) {
		     request.setAttribute("errorMessage", "Please enter your Username");
		     hasValidationError = true;
		 }
		 else if(interests.length() == 0 || interests == null) {
		     request.setAttribute("errorMessage", "Please Select atleast one interest");
		     hasValidationError = true;
		 }
		 else if(password.length() == 0 || password == null) {
		     request.setAttribute("errorMessage", "Please Enter Your Password");
		     hasValidationError = true;
		 }
		 else if(!password.equalsIgnoreCase(reEnteredPassword)) {
		     request.setAttribute("errorMessage", "Your Password and Re-Entered Passwords do not Match");
		     hasValidationError = true;
		 }
		 else if(genres.length() == 0 || genres == null) {
		     request.setAttribute("errorMessage", "Please Select atleast one Genres");
		     hasValidationError = true;
		 }
		 			
		 return hasValidationError;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
