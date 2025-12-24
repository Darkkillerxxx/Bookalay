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

@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("ACTION => " + action);

        // Load registration page
        if ("registerNewUser".equalsIgnoreCase(action) || action == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Handle registration submit
        if ("register".equalsIgnoreCase(action)) {

            /* ---------- Child Info ---------- */
            String childName = request.getParameter("childName");
            String age = request.getParameter("age");
            String gender = request.getParameter("gender");
            String readingLevel = request.getParameter("readingLevel");

            String interests = request.getParameterValues("interests") != null
                    ? String.join(",", request.getParameterValues("interests"))
                    : "";

            /* ---------- Parent Info ---------- */
            String parentName = request.getParameter("parentName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            /* ---------- Login Info ---------- */
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String reEnteredPassword = request.getParameter("reEnteredPassword");

            /* ---------- Reading Preferences ---------- */
            String genres = request.getParameterValues("genres") != null
                    ? String.join(",", request.getParameterValues("genres"))
                    : "";

            String readingFrequency = request.getParameter("readingFrequency");
            String notes = request.getParameter("notes");

            /* ---------- Validation ---------- */
            boolean hasValidationError = formValidator(
                    request,
                    username,
                    password,
                    reEnteredPassword,
                    interests,
                    genres
            );

            // ❌ Validation failed → go back to JSP
            if (hasValidationError) {
                request.getRequestDispatcher("jsp/register.jsp")
                       .forward(request, response);
                return;
            }

            /* ---------- Save to DB ---------- */
            UserService userService = new UserServiceImpl();

            Transaction transaction = userService.insertUser(
                    username,
                    password,
                    childName,
                    age,
                    gender,
                    interests,
                    parentName,
                    phone,
                    email,
                    readingLevel,
                    genres,
                    readingFrequency,
                    notes
            );

            /* ---------- Success Toast ---------- */
            request.setAttribute("showToast", true);
            request.setAttribute("toastMessage", transaction.getMessage());

            request.getRequestDispatcher("RegisterController?action=registerNewUser")
                   .forward(request, response);
        }
    }

    /* ---------- VALIDATION METHOD ---------- */
    private boolean formValidator(HttpServletRequest request,
                                  String username,
                                  String password,
                                  String reEnteredPassword,
                                  String interests,
                                  String genres) {

        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please enter your Username");
            return true;
        }

        if (interests == null || interests.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please select at least one interest");
            return true;
        }

        if (password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please enter your Password");
            return true;
        }

        if (!password.equals(reEnteredPassword)) {
            request.setAttribute("errorMessage", "Password and Re-entered Password do not match");
            return true;
        }

        if (genres == null || genres.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please select at least one genre");
            return true;
        }

        return false;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
