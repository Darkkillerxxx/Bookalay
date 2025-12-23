<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login | Library</title>

<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

<!-- Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=Playfair+Display:wght@400;500;600&display=swap" rel="stylesheet">

<style>
    body {
        background: #f3f0ff;
        font-family: 'Inter', sans-serif;
    }

    .login-wrapper {
        max-width: 450px;
        margin: 80px auto;
    }

    .card-custom {
        border-radius: 20px;
        border: none;
        box-shadow: 0 4px 20px rgba(0,0,0,0.08);
        background: #ffffffcc;
        padding: 30px;
        backdrop-filter: blur(6px);
    }

    .main-title {
        font-size: 32px;
        font-weight: 700;
        color: #7a3cff;
        text-align: center;
    }

    .subtitle {
        text-align: center;
        color: #6b6a75;
        margin-bottom: 25px;
    }

    .section-title {
        font-weight: 600;
        font-size: 18px;
        color: #5b2ecc;
    }

    .btn-purple {
        background: linear-gradient(to right, #923bff, #3f5efb);
        border: none;
        padding: 12px;
        font-weight: 600;
        color: white;
        width: 100%;
        border-radius: 8px;
        margin-top: 10px;
    }

    .forgot-link {
        text-decoration: none;
        color: #7a3cff;
        font-weight: 500;
    }

    .forgot-link:hover {
        text-decoration: underline;
    }
</style>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        <% if (request.getAttribute("showToast") != null) { %>
            var toastEl = document.getElementById('liveToast');
            new bootstrap.Toast(toastEl).show();
        <% } %>
    });
</script>

</head>
<body>

<!-- Toast -->
<div class="toast-container position-fixed top-0 end-0 p-3">
  <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
    <div class="toast-header">
      <strong class="me-auto">Library System</strong>
      <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
    </div>
    <div class="toast-body">
      <c:out value="${toastMessage}" />
    </div>
  </div>
</div>

<div class="login-wrapper">
    
    <h2 class="main-title">Welcome Back</h2>
    <p class="subtitle">Access your Bookalay account</p>

    <div class="card card-custom">
		 <!-- Logo -->
	    <div class="text-center mb-3">
	        <img src="https://media.istockphoto.com/id/877235850/vector/book-icon.jpg?s=612x612&w=0&k=20&c=FSTH3SrcKKTSH09LLkucwABRWOKHRYPmEjxqBjEDjxc=" alt="Bookalay Logo" style="width: 150px;">
	    </div>
	    
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <c:out value="${errorMessage}"/>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <form action="AuthenticationController" method="post">
            <input type="hidden" name="action" value="authenticate"/>

            <label class="form-label">Username</label>
            <input type="text" class="form-control mb-3" name="username" placeholder="Enter username" required>

            <label class="form-label">Password</label>
            <input type="password" class="form-control mb-3" name="password" placeholder="Enter password" required>

            <button type="submit" class="btn btn-purple">
                <i class="bi bi-box-arrow-in-right me-2"></i>Sign In
            </button>

            <div class="text-center mt-3 text-muted">
                Don't have an account? 
                <a href="RegisterController?action=registerNewUser" class="forgot-link">Sign Up</a>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
