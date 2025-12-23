<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Bookalay Dashboard</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
body {
	background: #f8f9fc;
}

.card {
	border: none;
	border-radius: 15px;
}

.nav-link.active {
	font-weight: bold;
	border-bottom: 2px solid #6f42c1;
}

.chart-placeholder {
	height: 180px;
	background: #eef0f7;
	border-radius: 10px;
}
</style>
</head>
<body>

	<div class="container-fluid p-4">
		<%@ include file="adminDashboardCommon.jsp"%>

		<!-- Top Row -->
		<div class="row g-4">
			<!-- Left Card -->
			<div class="col-lg-6">
				<div class="card p-4 h-100">
					<h5 class="fw-bold">Website Audience Metrics</h5>
					<p class="text-muted small">Audience to which the users
						belonged while on the current date range.</p>

					<div class="row text-center mb-4">
						<div class="col">
							<h4 class="fw-bold">${adminDashboardMetrics.totalUsers}</h4>
							<p class="text-muted small">Users</p>
						</div>
						<div class="col">
							<h4 class="fw-bold">${adminDashboardMetrics.booksIssued}</h4>
							<p class="text-muted small">Books Issued</p>
						</div>
						<div class="col">
							<h4 class="fw-bold">${adminDashboardMetrics.booksRead}</h4>
							<p class="text-muted small">Books Read</p>
						</div>
						<div class="col">
							<h4 class="fw-bold">${adminDashboardMetrics.usersWaitingForApproval}</h4>
							<p class="text-muted small">Users Waiting for Approval</p>
						</div>
					</div>

					<div class="row text-center mb-4">
						<div class="col">
							<h4 class="fw-bold">${adminDashboardMetrics.inactiveUsers}</h4>
							<p class="text-muted small">In-Active Users</p>
						</div>
						<div class="col">
							<h4 class="fw-bold">${adminDashboardMetrics.overdueBooks}</h4>
							<p class="text-muted small">Overdue Books</p>
						</div>
						<div class="col">
							<h4 class="fw-bold">${adminDashboardMetrics.totalBooksAvailable}</h4>
							<p class="text-muted small">Total Books Available</p>
						</div>
						<div class="col">
							<h4 class="fw-bold">${adminDashboardMetrics.newBooksRequestedToday}</h4>
							<p class="text-muted small">New Books Requested</p>
						</div>
					</div>

					<div class="chart-placeholder w-100"></div>
				</div>
			</div>

			<!-- Right Side -->
			<div class="col-lg-6">
				<!-- Recent Book Issues -->
				<div class="card p-3">
					<div class="card-header bg-white">
						<h5 class="mb-0">Recent Book Issues</h5>
					</div>

					<div class="card-body p-0">
						<table class="table table-bordered table-striped mb-0">
							<thead class="table-light">
								<tr>
									<th>#</th>
									<th>Book Title</th>
									<th>Member</th>
									<th>Issue Date</th>
									<th>Due Date</th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${empty adminDashboardMetrics.recentIssues}">
										<tr>
											<td colspan="6" class="text-center">No recent requests</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${adminDashboardMetrics.recentIssues}"
											var="r" varStatus="i">
											<tr>
												<td>${i.count}</td>
												<td>${r.bookName}</td>
												<td>${r.parentName}</td>
												<td>${r.issuedDate}</td>
												<td>${r.dueDate}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>

						</table>
					</div>
				</div>

				<!-- Users Waiting Approval -->
				<div class="card p-3 mt-3">
					<div class="card-header bg-warning text-dark">
						<h5 class="mb-0">Users Waiting for Approval</h5>
					</div>

					<div class="card-body p-0">
						<table class="table table-striped mb-0">
							<thead class="table-light">
								<tr>
									<th>#</th>
									<th>User Name</th>
									<th>Email</th>
									<th>Action</th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${empty adminDashboardMetrics.usersForApproval}">
										<tr>
											<td colspan="6" class="text-center">No recent requests</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${adminDashboardMetrics.usersForApproval}"
											var="r" varStatus="i">
											<tr>
												<td>${i.count}</td>
												<td>${r.parentName}</td>
												<td>${r.email}</td>
												<td><button class="btn btn-success btn-sm">View
														Details</button></td>
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

		<!-- Middle Row -->
		<div class="row g-4 mt-3">

			<!-- Overdue Books -->
			<div class="col-lg-6">
				<div class="card p-3 mt-3">
					<div class="card-header bg-danger text-white">
						<h5 class="mb-0">Overdue Books</h5>
					</div>

					<div class="card-body p-0">
						<table class="table table-striped table-bordered mb-0">
							<thead class="table-light">
								<tr>
									<th>#</th>
									<th>Book Title</th>
									<th>Member Name</th>
									<th>Issue Date</th>
									<th>Due Date</th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${empty adminDashboardMetrics.booksOverdue}">
										<tr>
											<td colspan="6" class="text-center">No recent requests</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${adminDashboardMetrics.booksOverdue}"
											var="r" varStatus="i">
											<tr>
												<td>${i.count}</td>
												<td>${r.bookName}</td>
												<td>${r.parentName}</td>
												<td>${r.issuedDate}</td>
												<td>${r.dueDate}</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>

						</table>
					</div>
				</div>
			</div>

			<!-- Today's Return List -->
			<div class="col-lg-6">
				<div class="card p-3 mt-3 shadow-sm">
					<div class="card-header bg-primary text-white">
						<h5 class="mb-0">Today's Return List</h5>
					</div>

					<div class="card-body p-0">
						<div class="table-responsive">
							<table class="table table-striped table-bordered mb-0">
								<thead class="table-light">
									<tr>
										<th>#</th>
										<th>Member Name</th>
										<th>Book Title</th>
										<th>Due Date</th>
									</tr>
								</thead>

								<tbody>
									<c:choose>
										<c:when test="${empty adminDashboardMetrics.todayReturns}">
											<tr>
												<td colspan="5" class="text-center">No recent requests</td>
											</tr>
										</c:when>
										<c:otherwise>
											<c:forEach items="${adminDashboardMetrics.todayReturns}" var="r"
												varStatus="i">
												<tr>
													<td>${i.count}</td>
													<td>${r.parentName}</td>
													<td>${r.bookName}</td>
													<td>${r.dueDate}</td>
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

	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
