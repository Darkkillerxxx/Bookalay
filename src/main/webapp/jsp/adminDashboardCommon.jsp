<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Get the user object from session
    com.Bookalay.pojo.User user = (com.Bookalay.pojo.User) session.getAttribute("user");
    String username = (user != null) ? user.getUsername() : "Guest";
%>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Google+Sans:ital,opsz,wght@0,17..18,400..700;1,17..18,400..700&family=Montserrat:ital,wght@0,100..900;1,100..900&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
<style>
	body{
	  font-family: "Montserrat", sans-serif;
	}
</style>

<script>
document.addEventListener("DOMContentLoaded", function () {
    <% if (request.getAttribute("loginSuccess") != null) { %>

        const user = {
            userId: "<%= user.getUserId() %>",
            username: "<%= user.getUsername() %>",
            userType: "<%= user.getUserType() %>"
        };

        localStorage.setItem("user", JSON.stringify(user));
        console.log("Login successful → User saved to localStorage:", user);
    <% } %>
});
</script>


<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h3 class="fw-bold">Hi, <%= username %></h3>
        <p>Here is your Bookalay Dashboard.</p>
    </div>

    <div>
        <a href="AuthenticationController?action=logout" class="btn btn-danger">Logout</a>
    </div>
</div>
<!-- Navigation Tabs -->
<ul class="nav nav-tabs mb-4">
    <li class="nav-item"><a class="nav-link" href="AuthenticationController?action=home">Home</a></li>
    <li class="nav-item"><a class="nav-link" href="BooksController?action=manageBooks">Books Management</a></li>
    <li class="nav-item"><a class="nav-link" href="ManageApprovalsController?action=manageApprovals">Approvals</a></li>
    <li class="nav-item"><a class="nav-link" href="ManageUsersController?action=fetchAllUsers">Members/Users</a></li>
    <li class="nav-item"><a class="nav-link" href="RequestController?action=viewRequest">Requests</a></li>
</ul>