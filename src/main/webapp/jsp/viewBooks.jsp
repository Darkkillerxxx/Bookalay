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
<link rel="stylesheet" href="assets/css/viewBooks.css">

</head>

<body>
	<div class="container-fluid p-4">

		<!-- HEADER -->
		<h3 class="fw-bold">Hi, welcome back!</h3>
		<p>Your Bookalay Dashboard Overview.</p>

		<!-- NAVIGATION -->
		<ul class="nav nav-tabs mb-4">
			<li class="nav-item"><a class="nav-link" href="#">Home</a></li>
			<li class="nav-item"><a class="nav-link"
				href="DashboardController?action=manageBooks">Books Management</a></li>
			<li class="nav-item"><a class="nav-link" href="ManageApprovalsController?action=manageApprovals">Approvals</a></li>
			<li class="nav-item"><a class="nav-link" href="#">Members/Users</a></li>
			<li class="nav-item"><a class="nav-link" href="#">Issues /
					Return Management</a></li>
			<li class="nav-item"><a class="nav-link" href="#">Requests</a></li>
		</ul>

		<!-- MAIN CONTENT -->
		<div class="row">

			<!-- LEFT FILTER COLUMN -->
			<div class="col-md-3">
				<form action="DashboardController" method="GET">

					<input type="hidden" name="action" value="manageBooks" />

					<!-- Search Box -->
					<div class="filter-card mb-4">
						<label class="form-label fw-bold">Search</label> <input
							type="text" name="search" class="form-control"
							placeholder="Search Book">
					</div>

					<!-- Interests -->
					<div class="filter-card mb-4">
						<label class="form-label fw-bold">Interests</label>
						<div class="mt-2 ps-2">
							<c:forEach var="i" items="${fn:split(interestList, ',')}">
								<input type="checkbox" name="interests" value="${i}"> ${i}<br>
							</c:forEach>
						</div>
					</div>

					<!-- Genres -->
					<div class="filter-card mb-3">
						<label class="form-label fw-bold">Genres</label>
						<div class="mt-2 ps-2">
							<c:forEach var="g" items="${fn:split(genreList, ',')}">
								<input type="checkbox" name="genres" value="${g}"> ${g}<br>
							</c:forEach>
						</div>
					</div>

					<!-- APPLY BUTTON -->
					<button class="btn btn-primary w-100">Apply Filters</button>

				</form>

			</div>

			<!-- RIGHT COLUMN -->
			<div class="col-md-9">

				<div class="row g-4">

					<c:forEach var="book" items="${booksList}">
						<div class="col-md-4">
							<div class="card p-0 booksCard">

								<!-- Book Cover -->
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
											<p class="card-text text-muted">${book.summary}</p>
										</c:otherwise>
									</c:choose>

									<p class="text-sm mb-1">
										<strong>Author:</strong> ${book.author}
									</p>

									<p class="text-sm mb-1">
										<strong>Genre:</strong> ${book.genre}
									</p>

									<p class="text-sm mb-2">
										<strong>Interest:</strong> ${book.interest}
									</p>
								</div>
							</div>
						</div>
					</c:forEach>

				</div>
			</div>

		</div>
	</div>

</body>
</html>
