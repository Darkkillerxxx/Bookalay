<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${book.title}</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">

<style>
body {
	background: #f8f9fc;
	font-family: Arial, sans-serif;
}

.book-container {
	display: flex;
	gap: 40px;
	padding: 40px;
	background: #fff;
	border-radius: 12px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.book-img img {
	width: 280px;
	height: 380px;
	object-fit: cover;
	border-radius: 10px;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
}

.book-title {
	font-size: 26px;
	font-weight: 700;
	margin-bottom: 5px;
}

.author-block img {
	width: 40px;
	height: 40px;
	border-radius: 50%;
	object-fit: cover;
	margin-right: 10px;
}

.rating-stars {
	color: #f5a623;
	font-size: 18px;
}

.info-row strong {
	width: 150px;
	display: inline-block;
}

.edition-btn {
	border: 1px solid #cdd2d7;
	padding: 6px 15px;
	border-radius: 20px;
	background: white;
	margin-right: 8px;
	cursor: pointer;
}

.primary-button {
	background: #24a0ed;
	color: white;
	padding: 10px 25px;
	border-radius: 25px;
	border: none;
	margin-right: 10px;
}

.secondary-button {
	background: #fff;
	color: #24a0ed;
	padding: 10px 25px;
	border-radius: 25px;
	border: 1px solid #24a0ed;
}
</style>
</head>

<body>

	<div class="container-fluid p-4">
		<c:if test="${!isAdmin}">
			<%@ include file="parentDashboardCommon.jsp"%>
		</c:if>

		<c:if test="${isAdmin}">
			<%@ include file="adminDashboardCommon.jsp"%>
		</c:if>

		<div class="book-container mt-3">

			<!-- LEFT SECTION — BOOK IMAGE -->
			<div class="book-img">
				<img
					src="${book.coverArt != null ? book.coverArt : 'https://via.placeholder.com/300x400'}"
					alt="Book Image">
			</div>

			<!-- RIGHT SECTION — BOOK DETAILS -->
			<div class="book-details">

				<!-- Title -->
				<div class="book-title">${book.title}</div>

				<!-- Author -->
				<div class="d-flex align-items-center mt-1">
					<span> ${book.author}</span>
				</div>

				<!-- Summary -->
				<p class="mt-3 text-muted" style="max-width: 95%;">
					${book.summary}</p>

				<!-- Info Section -->
				<div class="mt-3">
					<div class="info-row mb-1">
						<strong>Added Date:</strong> ${book.dateAdded}
					</div>
					<div class="info-row mb-1">
						<strong>Language:</strong> English
					</div>

					<div class="info-row mb-1">
						<strong>Pages:</strong> ${book.pageCount}
					</div>
					<div class="info-row mb-1">
						<strong>Genre:</strong> ${book.genre}
					</div>
					<div class="info-row mb-1">
						<strong>Interest:</strong> ${book.interest}
					</div>
					<div class="info-row mb-1">
						<strong>Reading Level:</strong> ${book.readingDifficulty}
					</div>
					<c:if test="${isAdmin}">
						<div class="info-row mb-1 w-100">
							<strong>Available Copies:</strong> ${book.availableCopies}
						</div>
					</c:if>
				</div>


				<!-- Action Buttons -->
				<c:if test="${!isAdmin}">
					<div class="mt-4">
						<a
							href="RequestController?action=showRequestForm&bookId=${book.bookId}"
							class="btn btn-primary">Request this book</a>
					</div>
				</c:if>

				<c:if test="${isAdmin}">
					<div class="d-flex mt-3">
						<a href="BooksController?action=editBookForm"
							style="margin-right: 10px" class="btn btn-warning">Edit
							Details</a>
						<c:if test="${book.available}">
							<a
								href="BooksController?action=toggleAvailability&bookId=${book.bookId}"
								class="btn btn-danger">Inactivate this book</a>
						</c:if>
						<c:if test="${!book.available}">
							<a
								href="BooksController?action=toggleAvailability&bookId=${book.bookId}"
								class="btn btn-success">Activate this Book</a>
						</c:if>
					</div>
				</c:if>

			</div>

		</div>

	</div>

</body>
</html>
