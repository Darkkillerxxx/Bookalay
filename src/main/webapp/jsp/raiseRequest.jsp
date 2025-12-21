<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>Raise Book Request</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
</head>

<body class="bg-light">

	<div class="container-fluid mt-5">
		<%@ include file="parentDashboardCommon.jsp"%>
		
		<div class="card shadow-lg p-4"
			style="max-width: 1000px; margin: auto;">
			<h3 class="text-center mb-4">Raise Book Request</h3>

			<!-- FORM START -->
			<form action="RequestController?action=raiseRequest" method="post">

				<!-- Hidden Book ID -->
				<input type="hidden" name="book_id" value="${book.bookId}" />
				
				<!-- Request Date (readonly, auto-filled) -->
				<div class="mb-3">
					<label class="form-label">Book</label><br/> 
					<label class="fw-bold">${book.title}</label>
				</div>

				<!-- Request Date (readonly, auto-filled) -->
				<div class="mb-3">
					<label class="form-label">Request Date (Date you want the book to be issued)</label> <input
						type="date" class="form-control" name="request_date"/>
				</div>

				<!-- Notes -->
				<div class="mb-3">
					<label class="form-label">Notes (Optional)</label>
					<textarea class="form-control" name="notes" rows="3"
						placeholder="Add any notes for this request..."></textarea>
				</div>

				<!-- Submit Button -->
				<button class="btn btn-primary w-100">Submit Request</button>

			</form>
			<!-- FORM END -->

		</div>

	</div>

</body>
</html>
