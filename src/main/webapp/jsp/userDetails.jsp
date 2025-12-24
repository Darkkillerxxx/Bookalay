<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Bookalay Dashboard</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


<style>
.card-custom {
	border-radius: 12px;
	border: 1px solid #e3e3e3;
	background: #ffffff;
}

.section-title {
	font-size: 20px;
	font-weight: 600;
	margin-bottom: 45px !important;
	color: #2c3e50;
}

.label-title {
	font-size: 13px;
	color: #7b7b7b;
	text-transform: uppercase;
	margin-bottom: 5px;
}

.value-text {
	font-size: 16px;
	font-weight: 600;
	color: #2c3e50;
}

.badge-custom {
	background: #eef5ff;
	color: #0d6efd;
	padding: 5px 10px;
	border-radius: 8px;
	font-size: 13px;
	font-weight: 500;
}
</style>

<script>
	document
			.addEventListener(
					"DOMContentLoaded",
					function() {
<%if (request.getAttribute("showToast") != null && (boolean) request.getAttribute("showToast")) {%>
	var toastEl = document.getElementById('liveToast');
						var toast = new bootstrap.Toast(toastEl);
						toast.show();
<%}%>
	});
</script>

</head>
<body>

	<div class="toast-container position-fixed top-0 end-0 p-3">
		<div id="liveToast"
			class="toast
			         <%Boolean show = (Boolean) request.getAttribute("showToast");
if (show != null && show) {
	Boolean success = (Boolean) request.getAttribute("toastSuccess");
	if (success != null && success) {
		out.print("toast-success");
	} else {
		out.print("toast-fail");
	}
}%>"
			role="alert" aria-live="assertive" aria-atomic="true">

			<div class="toast-header">
				<strong class="me-auto">User Details</strong>
				<button type="button" class="btn-close" data-bs-dismiss="toast"
					aria-label="Close"></button>
			</div>

			<div class="toast-body">
				<c:out value="${toastMessage}" />
			</div>

		</div>
	</div>

	<div class="container-fluid p-4">

		<%@ include file="adminDashboardCommon.jsp"%>

		<div class="row">
			<div class="col-6">
				<!-- =================== COMBINED CARD =================== -->
				<div class="card card-custom p-4 mb-4">

					<!-- ========== PARENT SECTION ========== -->
					<h4 class="section-title">Parent Details</h4>

					<div class="row g-4 mb-4">

						<div class="col-md-4">
							<div class="label-title">Name</div>
							<div class="value-text">${parent.parentName}</div>
						</div>

						<div class="col-md-4">
							<div class="label-title">Email</div>
							<div class="value-text">${parent.email}</div>
						</div>

						<div class="col-md-4">
							<div class="label-title">Phone</div>
							<div class="value-text">${parent.phone}</div>
						</div>

						<div class="col-md-4">
							<div class="label-title">Registration Date</div>
							<div class="value-text">${parent.registrationDate}</div>
						</div>

						<div class="col-md-4">
							<div class="label-title">Status</div>

							<c:choose>
								<c:when test="${parent.isActive}">
									<span class="badge bg-success">Active</span>
								</c:when>
								<c:otherwise>
									<span class="badge bg-danger">Inactive</span>
								</c:otherwise>
							</c:choose>
						</div>


					</div>

					<hr class="my-4" />

					<!-- ========== CHILD SECTION ========== -->
					<h4 class="section-title">Children Details</h4>

					<c:forEach var="child" items="${parent.childUsers}">
						<div class="row g-4 mb-4 p-3 border rounded bg-light">

							<div class="col-md-4">
								<div class="label-title">Name</div>
								<div class="value-text">${child.childName}</div>
							</div>

							<div class="col-md-4">
								<div class="label-title">Age</div>
								<div class="value-text">${child.age}</div>
							</div>

							<div class="col-md-4">
								<div class="label-title">Gender</div>
								<div class="value-text">${child.gender}</div>
							</div>

							<div class="col-md-4">
								<div class="label-title">Reading Interests</div>
								<span class="badge-custom"> ${child.interests} </span>
							</div>

							<div class="col-md-4">
								<div class="label-title">Reading Level</div>
								<div class="value-text">${child.readingLevel}</div>
							</div>

							<div class="col-md-4">
								<div class="label-title">Genres</div>
								<span class="badge-custom"> ${child.genres} </span>
							</div>

							<div class="col-md-4">
								<div class="label-title">Reading Frequency</div>
								<div class="value-text">${child.readingFrequency}</div>
							</div>

							<div class="col-md-12">
								<div class="label-title">Notes</div>
								<div class="value-text">${child.notes}</div>
							</div>
						</div>
					</c:forEach>

				</div>



				<div class="d-flex justify-content-evenly bd-highlight">
					<c:choose>
						<c:when test="${parent.isActive}">
							<a
								href="ManageUsersController?action=activateDeactivateUser&userId=${parent.parentId}&isActive=false"
								class="btn btn-danger px-4 py-2 bd-highlight"> De-Activate
								User </a>
						</c:when>
						<c:otherwise>
							<a
								href="ManageUsersController?action=activateDeactivateUser&userId=${parent.parentId}&isActive=true"
								class="btn btn-success px-4 py-2 bd-highlight"> Activate
								User</a>
						</c:otherwise>
					</c:choose>
				</div>

			</div>
			<div class="col-6">
				<div class="card w-100 card-custom mb-4">

					<h4 class="text-white bg-warning p-2">Request History</h4>

					<div class="table-responsive"
						style="max-height: 180px; overflow-y: auto;">
						<table class="table table-bordered table-striped mb-0">
							<thead class="table-light sticky-top">
								<tr>
									<th>#</th>
									<th>Book Name</th>
									<th>Requested Date</th>
									<th>Status</th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${empty dashboardMetrics.recentBookRequests}">
										<tr>
											<td colspan="6" class="text-center">No requests found.</td>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach items="${dashboardMetrics.recentBookRequests}"
											var="r" varStatus="i">
											<tr>
												<td>${i.count}</td>
												<td>${r.bookName}</td>
												<td>${r.requestDate}</td>
												<td><span class="badge bg-secondary">${r.status}</span>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>

				</div>

				<div class="card w-100 card-custom mb-4">

					<h4 class="text-white bg-primary p-2">Issued Book History</h4>

					<div class="table-responsive"
						style="max-height: 180px; overflow-y: auto;">
						<table class="table table-bordered table-striped mb-0">
							<thead class="table-light sticky-top">
								<tr>
									<th>#</th>
									<th>Book</th>
									<th>Issue Date</th>
									<th>Due Date</th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when
										test="${empty dashboardMetrics.booksCurrentlyIssuedList}">
										<tr>
											<td colspan="5" class="text-center">No books currently
												issued</td>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach
											items="${dashboardMetrics.booksCurrentlyIssuedList}" var="b"
											varStatus="i">
											<tr>
												<td>${i.count}</td>
												<td>${b.bookName}</td>
												<td>${b.issuedDate}</td>
												<td>${b.dueDate}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>

				</div>


				<div class="card w-100 card-custom mb-4">

					<h4 class="text-white bg-danger p-2">Late Returns History</h4>

					<div class="table-responsive"
						style="max-height: 180px; overflow-y: auto;">
						<table class="table table-bordered table-striped mb-0">
							<thead class="table-light sticky-top">
								<tr>
									<th>#</th>
									<th>Book Title</th>
									<th>Issue Date</th>
									<th>Due Date</th>
									<th>Returned Date</th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${empty dashboardMetrics.lateReturns}">
										<tr>
											<td colspan="5" class="text-center">No late returns
												found</td>
										</tr>
									</c:when>

									<c:otherwise>
										<c:forEach items="${dashboardMetrics.lateReturns}" var="r"
											varStatus="i">
											<tr>
												<td>${i.count}</td>
												<td>${r.bookName}</td>
												<td>${r.issuedDate}</td>
												<td>${r.dueDate}</td>
												<td>${r.returnedDate}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>

				</div>

			</div>
		</div>

	</div>

</body>
</html>
