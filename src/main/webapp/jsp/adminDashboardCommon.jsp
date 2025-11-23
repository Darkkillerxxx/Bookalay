<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Get the user object from session
    com.Bookalay.pojo.User user = (com.Bookalay.pojo.User) session.getAttribute("user");
    String username = (user != null) ? user.getUsername() : "Guest";
%>

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
    <li class="nav-item"><a class="nav-link" href="#">Home</a></li>
    <li class="nav-item"><a class="nav-link" href="DashboardController?action=manageBooks">Books Management</a></li>
    <li class="nav-item"><a class="nav-link" href="ManageApprovalsController?action=manageApprovals">Approvals</a></li>
    <li class="nav-item"><a class="nav-link" href="#">Members/Users</a></li>
    <li class="nav-item"><a class="nav-link" href="#">Issues / Return Management</a></li>
    <li class="nav-item"><a class="nav-link" href="#">Requests</a></li>
</ul>
