<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Update Profile</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>


<style>
body {
	background: #f8f9fc;
}

.card {
	border: none;
	border-radius: 15px;
}
</style>

</head>

<script type="text/javascript">
	document.addEventListener("DOMContentLoaded", function () {
	    <% if (request.getAttribute("showToast") != null) { %>
	        var toastEl = document.getElementById('liveToast');
	        var toast = new bootstrap.Toast(toastEl);
	        toast.show();
	    <% } %>
	});
</script>

<body>

	<div class="container-fluid p-4">
		<div class="toast-container position-fixed top-0 end-0 p-3">
			<div id="liveToast" class="toast" role="alert" aria-live="assertive"
				aria-atomic="true">
				<div class="toast-header">
					<strong class="me-auto">Library System</strong>
					<button type="button" class="btn-close" data-bs-dismiss="toast"
						aria-label="Close"></button>
				</div>
				<div class="toast-body">
					<c:out value="${transaction.message}" />
				</div>
			</div>
		</div>
		<%@ include file="parentDashboardCommon.jsp"%>

		<div class="row">
			<div class="col-12">

				<div class="card p-0 p-3 mt-3">

					<div
						class="card-header bg-light d-flex justify-content-between align-items-center">
						<h5 class="mb-0">Update Profile</h5>
					</div>

					<div class="card-body">

						<form action="ManageUsersController" method="post">
							<input type="hidden" name="action" value="updateProfile" /> <input
								type="hidden" name="parentId" value="${profile.parent.parentId}">
							<input type="hidden" name="childId"
								value="${profile.child.childId}"> <input type="hidden"
								name="userId" value="${profile.user.userId}">

							<!-- CHILD INFO -->
							<h6 class="mt-3 fw-bold">Child Information</h6>

							<div class="row g-3 mt-1">
								<div class="col-md-6">
									<label class="form-label">Child Name *</label> <input
										type="text" name="childName"
										value="${profile.child.childName}" class="form-control"
										required>
								</div>

								<div class="col-md-6">
									<label class="form-label">Age *</label> <select
										class="form-select" name="age" required>
										<option value="">Select</option>
										<c:forEach var="i" begin="5" end="15">
											<option value="${i}"
												${profile.child.age == i ? "selected" : ""}>${i}</option>
										</c:forEach>
									</select>
								</div>

								<div class="col-md-6">
									<label class="form-label">Gender *</label> <select
										class="form-select" name="gender" required>
										<option value="">Select</option>
										<option value="Boy"
											${profile.child.gender == 'Boy' ? "selected" : ""}>Boy</option>
										<option value="Girl"
											${profile.child.gender == 'Girl' ? "selected" : ""}>Girl</option>
									</select>
								</div>
							</div>

							<!-- INTERESTS -->
							<h6 class="mt-4 fw-bold">Interests</h6>

							<div class="row">
								<c:forEach var="item"
									items="${fn:split('Animals,Sports,Science,Art,Music,Technology,Nature,Adventure', ',')}">
									<div class="col-md-4">
										<input type="checkbox" name="interests" value="${item}"
											<c:if test="${fn:contains(profile.child.interests, item)}">checked</c:if>>
										${item}
									</div>
								</c:forEach>
							</div>

							<hr />

							<!-- PARENT INFO -->
							<h6 class="fw-bold">Parent Information</h6>

							<div class="row g-3 mt-1">
								<div class="col-md-6">
									<label class="form-label">Parent Name *</label> <input
										type="text" name="parentName"
										value="${profile.parent.parentName}" class="form-control"
										required>
								</div>

								<div class="col-md-6">
									<label class="form-label">Phone</label> <input type="text"
										name="phone" value="${profile.parent.phone}"
										class="form-control">
								</div>

								<div class="col-md-6">
									<label class="form-label">Email *</label> <input type="email"
										name="email" value="${profile.parent.email}"
										class="form-control" required>
								</div>
							</div>

							<hr />

							<!-- LOGIN DETAILS -->
							<h6 class="fw-bold">Login Details</h6>

							<div class="row g-3">
								<div class="col-md-6">
									<label class="form-label">Username *</label> <input type="text"
										name="username" value="${profile.user.username}"
										class="form-control" required>
								</div>
							</div>

							<hr />

							<!-- READING PREFERENCES -->
							<h6 class="fw-bold">Reading Preferences</h6>

							<label class="form-label">Reading Level</label> <select
								class="form-select mb-3" name="readingLevel">
								<option value="">Select</option>
								<option ${profile.child.readingLevel=='Beginner'?'selected':''}>Beginner</option>
								<option
									${profile.child.readingLevel=='Intermediate'?'selected':''}>Intermediate</option>
								<option ${profile.child.readingLevel=='Expert'?'selected':''}>Expert</option>
							</select>

							<!-- GENRES -->
							<h6 class="fw-bold">Genres</h6>
							<div class="row">
								<c:forEach var="g"
									items="${fn:split('Fiction,Non-fiction,Fantasy,Mystery,Science Fiction,Biography,Comics,Poetry', ',')}">
									<div class="col-md-4">
										<input type="checkbox" name="genres" value="${g}"
											<c:if test="${fn:contains(profile.child.genres, g)}">checked</c:if>>
										${g}
									</div>
								</c:forEach>
							</div>

							<label class="form-label mt-3">Reading Frequency</label> <input
								type="text" class="form-control mb-3" name="readingFrequency"
								value="${profile.child.readingFrequency}"> <label
								class="form-label">Notes</label>
							<textarea class="form-control" rows="3" name="notes">${profile.child.notes}</textarea>
							
							<div class="mt-4 d-flex justify-content-end">
								<button class="btn btn-primary w-25">Update Profile</button>
							</div>
						</form>
					</div>
				</div>

				

			</div>
		</div>

	</div>

</body>
</html>
