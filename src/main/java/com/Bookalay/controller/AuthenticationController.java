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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String action = request.getParameter("action");
		    
		    if(action == null) {
		        System.out.println("No Action Found");
		        return;
		    }
		    
		    switch(action.toLowerCase()) {
		        case "authenticate":
		            String username = request.getParameter("username");
		            String password = request.getParameter("password");
		            
		            UserService userService = new UserServiceImpl();
		            User user = userService.checkLogin(username, password);
		            
		            if(user != null) {
		                System.out.println("User Found: " + user.getUserType());
		                HttpSession session = request.getSession();
		                session.setAttribute("user", user);
	                    request.setAttribute("loginSuccess", true);
	                    
		                if(user.getUserType().equalsIgnoreCase("admin")) {
		                    RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/adminDashboard.jsp");
		                    dispatcher.forward(request, response);
		                } else {
		                    RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/parentDashboard.jsp");
		                    dispatcher.forward(request, response);
		                }
		            } else {
		                request.setAttribute("errorMessage", "Invalid Username Or Password");
		                RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/login.jsp");
		                dispatcher.forward(request, response);
		            }
		            break;
		            
		        case "logout":
		        	HttpSession session = request.getSession(false); // get existing session, don't create new
		            if (session != null) {
		                session.invalidate(); // invalidate session
		            }
		            
		            // Redirect to login page with JS to clear localStorage
		            response.setContentType("text/html");
		            response.getWriter().println("<script>");
		            response.getWriter().println("localStorage.removeItem('user');"); // clear user from localStorage
		            response.getWriter().println("alert('You have been logged out');");
		            response.getWriter().println("window.location.href='jsp/login.jsp';");
		            response.getWriter().println("</script>");
		        	break;
		        	
		        case "home":
		    		com.Bookalay.pojo.User loggedInUser = (com.Bookalay.pojo.User) request.getSession().getAttribute("user");
		    		if(loggedInUser.getUserType() == "admin") {
		    			 RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/adminDashboard.jsp");
	                     dispatcher.forward(request, response);
		    		}else {
		    			 RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/parentDashboard.jsp");
		                 dispatcher.forward(request, response);
		    		}
		        	break;
		        	
		        default:
		            System.out.println("No Action Found");
		            break;
		    }
		}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
