package com.Bookalay.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Bookalay.pojo.ParentDashboardData;
import com.Bookalay.pojo.User;
import com.Bookalay.pojo.AdminDashboardData;
import com.Bookalay.pojo.Book;
import com.Bookalay.service.BookService;
import com.Bookalay.service.DashboardService;
import com.Bookalay.service.UserService;
import com.Bookalay.serviceImpl.BookServiceImpl;
import com.Bookalay.serviceImpl.DashboardServiceImpl;
import com.Bookalay.serviceImpl.UserServiceImpl;
import com.Bookalay.util.UserUtil;

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
		                	setAdminMetrics(request,response);
		                } else {
		                	setParentMetrics(user.getUserId(),request,response);
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
		            response.getWriter().println("window.location.href='/Bookalay';");
		            response.getWriter().println("</script>");
		        	break;
		        	
		        case "home":
		    		com.Bookalay.pojo.User loggedInUser = (com.Bookalay.pojo.User) request.getSession().getAttribute("user");
		    	
		    		
		    		if("admin".equalsIgnoreCase(loggedInUser.getUserType())) {
	                	setAdminMetrics(request,response);
		    		}else {
		    			 setParentMetrics(loggedInUser.getUserId(),request,response);
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
	
	private void setParentMetrics(Integer userId,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		DashboardService dashboardService = new DashboardServiceImpl();
		BookService bookService = new BookServiceImpl();
		 
		 ParentDashboardData pdd = new ParentDashboardData();
         Integer parentId = UserUtil.getParentIdByUserId(userId);
		 
		 pdd.setTotalBooksRequested(dashboardService.getTotalRequests(parentId));
		 pdd.setBooksCurrentlyIssued(dashboardService.getBooksCurrentlyIssued(parentId));
		 pdd.setBooksOverdue(dashboardService.getOverdueBooksCount(parentId));
		 pdd.setBooksReturned(dashboardService.getBooksReturned(parentId));
		 pdd.setRecentBookRequests(dashboardService.getRecentRequests(parentId, true));
		 pdd.setBooksCurrentlyIssuedList(dashboardService.getIssuedBooks(parentId, true));
		 pdd.setOverdueBooks(dashboardService.getOverdueBooks(parentId));
		 pdd.setReturnedBooksHistory(dashboardService.getReturnedBooks(parentId));
		 pdd.setUpcomingReturns(dashboardService.getUpcomingDue(parentId, 7));
		
		 List<Book> recommendedBooksList = bookService.recommendBooksForParent(userId);
		 request.setAttribute("recommendedBooks", recommendedBooksList);
		 
		 System.out.print(recommendedBooksList);
		 
		 request.setAttribute("dashboardMetrics", pdd);
		 RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/parentDashboard.jsp");
         dispatcher.forward(request, response);
	}
	
	private void setAdminMetrics(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		DashboardService dashboardService = new DashboardServiceImpl();
		 
		 AdminDashboardData add = new AdminDashboardData();

		 add.setTotalUsers(dashboardService.getTotalUsersForAdmin());
		 add.setBooksIssued(dashboardService.getTotalBooksIssuedForAdmin());
		 add.setBooksRead(dashboardService.getTotalBooksReadForAdmin());
		 add.setUsersWaitingForApproval(dashboardService.getUsersWaitingForApproval());
		 add.setInactiveUsers(dashboardService.getInactiveUsersForAdmin());
		 add.setOverdueBooks(dashboardService.getOverdueBooksForAdmin());
		 add.setTotalBooksAvailable(dashboardService.getTotalBooksAvailableForAdmin());
		 add.setNewBooksRequestedToday(dashboardService.getNewBooksRequestedToday());
		 add.setUpcomingReturns(dashboardService.getUpcomingDueRequestForAdmin(8));
		 add.setTodayReturns(dashboardService.getTodayReturnRequestForAdmin());
		 add.setRecentIssues(dashboardService.getRecentBookIssuesForAdmin());
		 add.setBooksOverdue(dashboardService.getOverdueBooksAdmin());
		 add.setUsersForApproval(dashboardService.getUsersForApprovalForAdmin());
		 
		 request.setAttribute("adminDashboardMetrics", add);
		 
		 RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/adminDashboard.jsp");
         dispatcher.forward(request, response);
	}

}
