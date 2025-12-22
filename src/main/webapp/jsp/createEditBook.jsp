<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>
	<c:choose>
		<c:when test="${not empty book}">Edit Book</c:when>
		<c:otherwise>Add New Book</c:otherwise>
	</c:choose>
</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	  rel="stylesheet" />

<style>
/* 🔒 UNCHANGED */
body {
	background: #eef1f5;
	font-family: 'Segoe UI', sans-serif;
}
.card { border-radius: 16px; border: none; overflow: hidden; }
.card-header { background: #ffffff; border-bottom: 1px solid #e0e0e0; padding: 20px; }
.card-header h3 { margin: 0; font-weight: 700; color: #333; font-size: 1.4rem; }
.form-label { font-weight: 600; color: #444; }
.form-control { border-radius: 10px; padding: 10px 12px; }
.btn-primary-custom { background: #4A6CF7; border: none; border-radius: 10px; padding: 10px 25px; font-weight: 600; }
.btn-secondary-custom { border-radius: 10px; padding: 10px 25px; font-weight: 600; }
</style>
</head>

<body>

<div class="container-fluid p-4">

	<%@ include file="adminDashboardCommon.jsp"%>

	<div class="row justify-content-center">
		<div class="col-md-10 col-lg-8">

			<div class="card shadow">
				<div class="card-header text-center">
					<h3>
						<c:choose>
							<c:when test="${not empty book}">Edit Book</c:when>
							<c:otherwise>Add New Book</c:otherwise>
						</c:choose>
					</h3>
				</div>

				<div class="card-body p-4">

					<form action="BooksController" method="post">

						<input type="hidden" name="action"
							value="${not empty book ? 'editBook' : 'saveBook'}" />

						<c:if test="${not empty book}">
							<input type="hidden" name="bookId" value="${book.bookId}" />
						</c:if>

						<div class="row g-3">

							<!-- Title -->
							<div class="col-md-6">
								<label class="form-label">Book Title *</label>
								<input type="text" name="title" class="form-control"
									required value="${not empty book ? book.title : ''}" />
							</div>

							<!-- Author -->
							<div class="col-md-6">
								<label class="form-label">Author *</label>
								<input type="text" name="author" class="form-control"
									required value="${not empty book ? book.author : ''}" />
							</div>

							<!-- Interest -->
							<div class="col-6">
								<label class="form-label fw-bold">Interests</label>
								<div class="p-3 border rounded bg-white">

									<c:forEach var="i"
										items="${fn:split('Animals,Sports,Science,Art,Music,Technology,Nature,Adventure', ',')}">
										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="interest" value="${i}"
												<c:if test="${not empty book && fn:contains(book.interest, i)}">checked</c:if>>
											<label class="form-check-label">${i}</label>
										</div>
									</c:forEach>

								</div>
							</div>

							<!-- Genre -->
							<div class="col-6 mt-3">
								<label class="form-label fw-bold">Genres</label>
								<div class="p-3 border rounded bg-white">

									<c:forEach var="g"
										items="${fn:split('Fiction,Non-fiction,Fantasy,Mystery,Science Fiction,Biography,Comics,Poetry', ',')}">
										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="genre" value="${g}"
												<c:if test="${not empty book && fn:contains(book.genre, g)}">checked</c:if>>
											<label class="form-check-label">${g}</label>
										</div>
									</c:forEach>

								</div>
							</div>

							<!-- Reading Difficulty -->
							<div class="col-md-6">
								<label class="form-label">Reading Difficulty</label>
								<select class="form-select mb-3" name="reading_difficulty">
									<option value="">Select Reading Difficulty</option>
									<option ${book.readingDifficulty == 'Beginner' ? 'selected' : ''}>Beginner</option>
									<option ${book.readingDifficulty == 'Intermediate' ? 'selected' : ''}>Intermediate</option>
									<option ${book.readingDifficulty == 'Expert' ? 'selected' : ''}>Expert</option>
								</select>
							</div>

							<!-- Series Name -->
							<div class="col-md-6">
								<label class="form-label">Series Name</label>
								<input type="text" name="series_name" class="form-control"
									value="${not empty book ? book.seriesName : ''}" />
							</div>

							<!-- Page Count -->
							<div class="col-md-6">
								<label class="form-label">Page Count</label>
								<input type="number" name="page_count" class="form-control"
									min="1" value="${not empty book ? book.pageCount : ''}" />
							</div>

							<!-- Total Copies -->
							<div class="col-md-6">
								<label class="form-label">Total Copies *</label>
								<input type="number" name="total_copies" class="form-control"
									required min="1"
									value="${not empty book ? book.totalCopies : ''}" />
							</div>

							<!-- Available Copies -->
							<div class="col-md-6">
								<label class="form-label">Available Copies *</label>
								<input type="number" name="available_copies"
									class="form-control" required min="0"
									value="${not empty book ? book.availableCopies : ''}" />
							</div>

							<!-- Summary -->
							<div class="col-12">
								<label class="form-label">Summary</label>
								<textarea name="summary" class="form-control" rows="3">${not empty book ? book.summary : ''}</textarea>
							</div>

							<!-- Cover Art -->
							<div class="col-12">
								<label class="form-label">Cover Art URL *</label>
								<input type="url" name="cover_art" class="form-control"
									required value="${not empty book ? book.coverArt : ''}" />
							</div>

						</div>

						<div class="text-center mt-4">
							<button type="submit" class="btn btn-primary-custom">
								<c:choose>
									<c:when test="${not empty book}">Update Book</c:when>
									<c:otherwise>Add Book</c:otherwise>
								</c:choose>
							</button>

							<a href="AdminDashboard.jsp"
								class="btn btn-secondary btn-secondary-custom">Cancel</a>
						</div>

					</form>

				</div>
			</div>

		</div>
	</div>
</div>

</body>
</html>
