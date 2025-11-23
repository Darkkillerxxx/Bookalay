package com.Bookalay.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bookalay.pojo.Book;
import com.Bookalay.service.BookService;
import com.Bookalay.serviceImpl.BookServiceImpl;

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
		case "manageBooks":

			String search = request.getParameter("search");

			// Multiple checkboxes → arrays
			String[] interestsArr = request.getParameterValues("interests");
			String[] genresArr = request.getParameterValues("genres");

			// Convert arrays → comma separated lists
			String interests = (interestsArr != null) ? String.join(",", interestsArr) : null;
			String genres = (genresArr != null) ? String.join(",", genresArr) : null;

			BookService bookService = new BookServiceImpl();
			List<Book> fetchedBooksList = bookService.fetchAvailableBooks(search, interests, genres);

			request.setAttribute("booksList", fetchedBooksList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/viewBooks.jsp");
			dispatcher.forward(request, response);

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
