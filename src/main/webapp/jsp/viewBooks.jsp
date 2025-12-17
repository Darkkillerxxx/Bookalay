<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="interestList"
	value="Animals,Sports,Science,Art,Music,Technology,Nature,Adventure" />
<c:set var="genreList"
	value="Fiction,Non-fiction,Fantasy,Mystery,Science Fiction,Biography,Comics,Poetry" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Bookalay Dashboard</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
body {
	background-color: #f8f9fc;
}

.booksCard {
	cursor: pointer;
	transition: transform 0.2s;
	height: 100%;
	display: flex;
	flex-direction: column;
}

.booksCard:hover {
	transform: scale(1.03);
}

.card-img-top {
	height: 250px;
	object-fit: fit;
}

.card-body {
	flex: 1;
	display: flex;
	flex-direction: column;
}

.card-body h5 {
	min-height: 3em; /* Ensure title area is consistent */
}

.book-summary {
	font-size: 0.9rem;
	flex: 1;
	overflow: hidden;
}

.card-footer {
	font-size: 0.85rem;
	color: #555;
}

.filter-card {
	background: #ffffff;
	padding: 15px;
	border-radius: 10px;
	box-shadow: 0px 2px 6px rgba(0, 0, 0, 0.1);
}
</style>
</head>

<body>
	<div class="container-fluid p-4">

		<c:if test="${isAdmin}">
			<%@ include file="adminDashboardCommon.jsp"%>

			<div class="d-flex w-100 justify-content-end mb-4">
				<a href="BooksController?action=createEditBooks" class="btn btn-primary">Create New Book</a>
			</div>
		</c:if>

		<c:if test="${!isAdmin}">
			<%@ include file="parentDashboardCommon.jsp"%>
		</c:if>



		<div class="row">
			<!-- LEFT FILTER COLUMN -->
			<div class="col-md-3">
				<form action="BooksController" method="GET">
					<input type="hidden" name="action" value="manageBooks" />

					<div class="filter-card mb-4">
						<label class="form-label fw-bold">Search</label> <input
							type="text" name="search" class="form-control"
							placeholder="Search Book">
					</div>

					<div class="filter-card mb-4">
						<label class="form-label fw-bold">Interests</label>
						<div class="mt-2 ps-2">
							<c:forEach var="i" items="${fn:split(interestList, ',')}">
								<input type="checkbox" name="interests" value="${i}"> ${i}<br>
							</c:forEach>
						</div>
					</div>

					<div class="filter-card mb-3">
						<label class="form-label fw-bold">Genres</label>
						<div class="mt-2 ps-2">
							<c:forEach var="g" items="${fn:split(genreList, ',')}">
								<input type="checkbox" name="genres" value="${g}"> ${g}<br>
							</c:forEach>
						</div>
					</div>

					<button class="btn btn-primary w-100">Apply Filters</button>
				</form>
			</div>

			<!-- RIGHT COLUMN -->
			<div class="col-md-9">
				<div class="row g-4">

					<c:forEach var="book" items="${booksList}">
						<div class="col-md-3 d-flex">
							<a
								href="DashboardController?action=viewBooksDetails&bookId=${book.bookId}"
								style="text-decoration: none; color: inherit; width: 100%;">
								<div class="card p-0 booksCard">
									<img
										src="${book.coverArt != null ? book.coverArt : 'https://via.placeholder.com/300x180'}"
										class="card-img-top" alt="Book Cover">
									<div class="card-body">
										<h5 class="card-title fw-bold">${book.title}</h5>
										<c:choose>
											<c:when test="${fn:length(book.summary) > 100}">
												<p class="card-text text-muted book-summary">
													${fn:substring(book.summary, 0, 100)}...</p>
											</c:when>
											<c:otherwise>
												<p class="card-text text-muted book-summary">${book.summary}</p>
											</c:otherwise>
										</c:choose>
										<div class="card-footer mt-2">
											<p class="mb-1">
												<strong>Author:</strong> ${book.author}
											</p>
											<p class="mb-1">
												<strong>Genre:</strong> ${book.genre}
											</p>
											<p class="mb-0">
												<strong>Interest:</strong> ${book.interest}
											</p>
										</div>
									</div>
								</div>
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
