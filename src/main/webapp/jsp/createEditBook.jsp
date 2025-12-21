<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Book</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet" />

<style>
body {
	background: #eef1f5;
	font-family: 'Segoe UI', sans-serif;
}

.card {
	border-radius: 16px;
	border: none;
	overflow: hidden;
}

.card-header {
	background: #ffffff;
	border-bottom: 1px solid #e0e0e0;
	padding: 20px;
}

.card-header h3 {
	margin: 0;
	font-weight: 700;
	color: #333;
	font-size: 1.4rem;
}

.form-label {
	font-weight: 600;
	color: #444;
}

.form-control {
	border-radius: 10px;
	padding: 10px 12px;
}

.btn-primary-custom {
	background: #4A6CF7;
	border: none;
	border-radius: 10px;
	padding: 10px 25px;
	font-weight: 600;
}

.btn-secondary-custom {
	border-radius: 10px;
	padding: 10px 25px;
	font-weight: 600;
}
</style>

<script type="text/javascript">
	document.addEventListener("DOMContentLoaded", function () {
	    <% if (request.getAttribute("showToast") != null) { %>
	        var toastEl = document.getElementById('liveToast');
	        var toast = new bootstrap.Toast(toastEl);
	        toast.show();
	    <% } %>
	});
</script>

</head>

<body>

	<div class="container-fluid p-4">
		
		<div class="toast-container position-fixed top-0 end-0 p-3">
		  <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
		    <div class="toast-header">
		      <strong class="me-auto">Library System</strong>
		      <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
		    </div>
		    <div class="toast-body">
		      <c:out value="${toastMessage}" />
		    </div>
		  </div>
		</div>
		
		<%@ include file="adminDashboardCommon.jsp"%>

		<div class="row justify-content-center">
			<div class="col-md-10 col-lg-8">

				<div class="card shadow">
					<div class="card-header text-center">
						<h3>Add New Book</h3>
					</div>

					<div class="card-body p-4">

						<form action="BooksController" method="post">
							<input type="hidden" name="action" value="saveBook" />

							<div class="row g-3">

								<!-- Title -->
								<div class="col-md-6">
									<label class="form-label">Book Title *</label> <input
										type="text" name="title" class="form-control" required />
								</div>

								<!-- Author -->
								<div class="col-md-6">
									<label class="form-label">Author *</label> <input type="text"
										name="author" class="form-control" required />
								</div>

								<!-- Interest -->
								<div class="col-6">
									<label class="form-label fw-bold">Interests</label>
									<div class="p-3 border rounded bg-white">

										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="interest" value="Animals"> <label
												class="form-check-label">Animals</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="interest" value="Sports"> <label
												class="form-check-label">Sports</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="interest" value="Science"> <label
												class="form-check-label">Science</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="interest" value="Art"> <label
												class="form-check-label">Art</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="interest" value="Music"> <label
												class="form-check-label">Music</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="interest" value="Technology"> <label
												class="form-check-label">Technology</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="interest" value="Nature"> <label
												class="form-check-label">Nature</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox"
												name="interest" value="Adventure"> <label
												class="form-check-label">Adventure</label>
										</div>

									</div>
								</div>


								<!-- Genre -->
								<div class="col-6 mt-3">
									<label class="form-label fw-bold">Genres</label>
									<div class="p-3 border rounded bg-white">

										<div class="form-check">
											<input class="form-check-input" type="checkbox" name="genre"
												value="Fiction"> <label class="form-check-label">Fiction</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox" name="genre"
												value="Non-fiction"> <label class="form-check-label">Non-fiction</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox" name="genre"
												value="Fantasy"> <label class="form-check-label">Fantasy</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox" name="genre"
												value="Mystery"> <label class="form-check-label">Mystery</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox" name="genre"
												value="Science Fiction"> <label
												class="form-check-label">Science Fiction</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox" name="genre"
												value="Biography"> <label class="form-check-label">Biography</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox" name="genre"
												value="Comics"> <label class="form-check-label">Comics</label>
										</div>

										<div class="form-check">
											<input class="form-check-input" type="checkbox" name="genre"
												value="Poetry"> <label class="form-check-label">Poetry</label>
										</div>

									</div>
								</div>


								<!-- Reading Difficulty -->
								<div class="col-md-6">
									<label class="form-label">Reading Difficulty</label> <select
										class="form-select mb-3" name="reading_difficulty">
										<option value="">Select Reading Difficulty</option>
										<option>Beginner</option>
										<option>Intermediate</option>
										<option>Expert</option>
									</select>
								</div>

								<!-- Series Name -->
								<div class="col-md-6">
									<label class="form-label">Series Name</label> <input
										type="text" name="series_name" class="form-control" />
								</div>

								<!-- Page Count -->
								<div class="col-md-6">
									<label class="form-label">Page Count</label> <input
										type="number" name="page_count" class="form-control" min="1" />
								</div>

								<!-- Total Copies -->
								<div class="col-md-6">
									<label class="form-label">Total Copies *</label> <input
										type="number" name="total_copies" class="form-control"
										required min="1" />
								</div>

								<!-- Available Copies -->
								<div class="col-md-6">
									<label class="form-label">Available Copies *</label> <input
										type="number" name="available_copies" class="form-control"
										required min="0" />
								</div>

								<!-- Summary -->
								<div class="col-12">
									<label class="form-label">Summary</label>
									<textarea name="summary" class="form-control" rows="3"></textarea>
								</div>

								<!-- Cover Art (URL) -->
								<div class="col-12">
									<label class="form-label">Cover Art URL *</label> <input
										type="url" name="cover_art" class="form-control"
										placeholder="https://example.com/book.jpg" required />
								</div>

							</div>

							<div class="text-center mt-4">
								<button type="submit" class="btn btn-primary-custom">Add
									Book</button>
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
