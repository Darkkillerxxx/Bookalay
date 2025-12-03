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
	width: 130px;
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

	<div class="container mt-4">
		<%@ include file="parentDashboardCommon.jsp"%>
		
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
					<img src="https://i.pravatar.cc/40" alt="Author"><span> ${book.author}</span>
				</div>

				<!-- Summary -->
				<p class="mt-3 text-muted" style="max-width: 480px;">
					${book.summary}</p>

				<!-- Info Section -->
				<div class="mt-3">

					<div class="info-row mb-1">
						<strong>Publisher:</strong> Thomas Nelson Inc.
					</div>
					<div class="info-row mb-1">
						<strong>Published:</strong> ${book.dateAdded}
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

				</div>


				<!-- Action Buttons -->
				<div class="mt-4">
					<a href="RequestController?action=showRequestForm&bookId=${book.bookId}" class="primary-button bg-primary">Request this book</a>
				</div>

			</div>

		</div>

	</div>

</body>
</html>
