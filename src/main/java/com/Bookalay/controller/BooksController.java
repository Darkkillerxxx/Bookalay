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
import com.Bookalay.pojo.Transaction;
import com.Bookalay.service.BookService;
import com.Bookalay.serviceImpl.BookServiceImpl;

/**
 * Servlet implementation class BooksController
 */
@WebServlet("/BooksController")
public class BooksController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BooksController() {
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

		switch (action) {
		case "manageBooks":
			fetchAllBooks(request,response);

			break;
		case "createEditBooks":
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/createEditBook.jsp");
			dispatcher.forward(request, response);
			break;

		case "saveBook":
			saveBook(request, response);
			break;
		
		case "editBookForm":
			System.out.print(57);
		    String bookIdStr = request.getParameter("bookId");
		    if (bookIdStr != null) {
		    	System.out.print(5777);
		        int bookId = Integer.parseInt(bookIdStr);
		        BookDaoImpl bookDao = new BookDaoImpl();
		        Book book = bookDao.getBookById(bookId);

		        request.setAttribute("book", book);
		        request.setAttribute("mode", "edit");
		        RequestDispatcher editFormdispatcher = request.getRequestDispatcher("jsp/createEditBook.jsp");
		        editFormdispatcher.forward(request, response);
		    }
		    break;

		
		case "toggleAvailability":
			toggleAvailability(request, response);
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
	
	private void fetchAllBooks(HttpServletRequest request, HttpServletResponse response) {
		try {
			com.Bookalay.pojo.User user = (com.Bookalay.pojo.User) request.getSession().getAttribute("user");
			System.out.print("48" + user.getUserType());
			boolean isAdminUserLoggedIn = false;
			if ("admin".equals(user.getUserType())) {
				request.getSession().setAttribute("isAdmin", true);
				isAdminUserLoggedIn = true;
			} else {
				request.getSession().setAttribute("isAdmin", false);
			}

			String search = request.getParameter("search");

			// Multiple checkboxes → arrays
			String[] interestsArr = request.getParameterValues("interests");
			String[] genresArr = request.getParameterValues("genres");

			// Convert arrays → comma separated lists
			String interests = (interestsArr != null) ? String.join(",", interestsArr) : null;
			String genres = (genresArr != null) ? String.join(",", genresArr) : null;

			BookService bookService = new BookServiceImpl();
			List<Book> fetchedBooksList = bookService.fetchAvailableBooks(search, interests, genres,isAdminUserLoggedIn);

			request.setAttribute("booksList", fetchedBooksList);

			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/viewBooks.jsp");
			dispatcher.forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void saveBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Book book = new Book();

			book.setTitle(request.getParameter("title"));
			book.setAuthor(request.getParameter("author"));
			String[] interests = request.getParameterValues("interest");
			String[] genres = request.getParameterValues("genre");

			String interestString = (interests != null) ? String.join(",", interests) : "";
			String genreString = (genres != null) ? String.join(",", genres) : "";
			book.setInterest(interestString);
			book.setGenre(genreString);
			book.setReadingDifficulty(request.getParameter("reading_difficulty"));
			book.setSeriesName(request.getParameter("series_name"));
			book.setPageCount(Integer.parseInt(request.getParameter("page_count")));
			book.setSummary(request.getParameter("summary"));
			book.setCoverArt(request.getParameter("cover_art"));
			book.setTotalCopies(Integer.parseInt(request.getParameter("total_copies")));
			book.setAvailableCopies(Integer.parseInt(request.getParameter("available_copies")));
			book.setAvailable(true);

			BookService dao = new BookServiceImpl();
			Transaction result = dao.createBook(book);
			
			request.setAttribute("showToast", true);
			request.setAttribute("transaction", result);
			
			if(result.isSuccess()) {
			    response.sendRedirect("BooksController?action=manageBooks");
			}else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/createEditBook.jsp");
				dispatcher.forward(request, response);
			}
		

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/createEditBook.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	// ================ TOGGLE AVAILABILITY =======================
		private void toggleAvailability(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			try {
				int bookId = Integer.parseInt(request.getParameter("bookId"));

				BookService service = new BookServiceImpl();
				Transaction result = service.toggleBookAvailability(bookId);

				request.setAttribute("message", result.getMessage());
				
				fetchAllBooks(request,response);

			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "Error updating availability");
				RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/error.jsp");
				dispatcher.forward(request, response);
			}
		}

}
