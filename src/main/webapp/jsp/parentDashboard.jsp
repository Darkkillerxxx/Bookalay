<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Parent Dashboard</title>

<style type="text/css">
<
style>.reco-card {
	height: 340px; /* FIXED HEIGHT */
	cursor: pointer;
	transition: all 0.3s ease;
	border-radius: 12px;
	overflow: hidden;
}

.reco-card:hover {
	transform: scale(1.05);
	box-shadow: 0 12px 25px rgba(0, 0, 0, 0.2);
}

.reco-card img {
	height: 220px;
	object-fit: fit;
}

.reco-card .card-body {
	padding: 10px;
}

.reco-card .card-title {
	font-size: 14px;
	font-weight: 600;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.reco-card .card-genre {
	font-size: 12px;
	color: #6c757d;
}
</style>


</style>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body class="container-fluid p-4">

	<!-- ✅ KEEP YOUR EXISTING HEADER / NAV -->
	<%@ include file="parentDashboardCommon.jsp"%>

	<div class="container-fluid p-4">

		<!-- TOP METRICS -->
		<div class="row g-4 mt-2">

			<div class="col">
				<div class="card p-3 text-center">
					<h4 class="fw-bold">${dashboardMetrics.totalBooksRequested}</h4>
					<p class="text-muted small">Total Books Requested</p>
				</div>
			</div>

			<div class="col">
				<div class="card p-3 text-center">
					<h4 class="fw-bold">${dashboardMetrics.booksCurrentlyIssued}</h4>
					<p class="text-muted small">Books Currently Issued</p>
				</div>
			</div>

			<div class="col">
				<div class="card p-3 text-center">
					<h4 class="fw-bold text-danger">${dashboardMetrics.booksOverdue}</h4>
					<p class="text-muted small">Books Overdue</p>
				</div>
			</div>

			<div class="col">
				<div class="card p-3 text-center">
					<h4 class="fw-bold">${dashboardMetrics.booksReturned}</h4>
					<p class="text-muted small">Books Returned</p>
				</div>
			</div>
		</div>

		<!-- RECOMMENDED BOOKS -->
		<!-- RECOMMENDED BOOKS -->
		<div class="row mt-4">
			<div class="col-12">
				<h5 class="fw-bold mb-3">Recommended Books</h5>
			</div>

			<c:choose>
				<c:when test="${empty recommendedBooks}">
					<div class="col-12">
						<div class="alert alert-info text-center">No recommendations
							available at the moment</div>
					</div>
				</c:when>

				<c:otherwise>
					<c:forEach items="${recommendedBooks}" var="book" begin="0" end="3">
						<div class="col-lg-2 col-md-6 mb-3">

							<a href="DashboardController?action=viewBooksDetails&bookId=${book.bookId}"
								class="text-decoration-none text-dark">

								<div class="card reco-card">

									<img
										src="${empty book.coverArt 
									? 'https://via.placeholder.com/200x250'
									: book.coverArt}"
										class="card-img-top" alt="${book.title}">

									<div class="card-body text-center">
										<h6 class="card-title mb-1">${book.title}</h6>
										<p class="card-genre mb-0">${book.genre}</p>
									</div>

								</div>
							</a>

						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>




		<!-- RECENT REQUESTS -->
		<div class="row g-4 mt-4">
			<div class="col-lg-6">
				<div class="card">
					<div class="card-header bg-white">
						<h5 class="mb-0">Recent Book Requests</h5>
					</div>

					<table class="table table-bordered table-striped mb-0">
						<thead>
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
										<td colspan="4" class="text-center">No recent requests</td>
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

			<!-- BOOKS CURRENTLY ISSUED -->
			<div class="col-lg-6">
				<div class="card">
					<div class="card-header bg-primary text-white">
						<h5 class="mb-0">Books Currently Issued</h5>
					</div>

					<table class="table table-bordered table-striped mb-0">
						<thead>
							<tr>
								<th>#</th>
								<th>Book</th>
								<th>Issue Date</th>
								<th>Due Date</th>
								<th>Status</th>
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
									<c:forEach items="${dashboardMetrics.booksCurrentlyIssuedList}"
										var="b" varStatus="i">
										<tr>
											<td>${i.count}</td>
											<td>${b.bookName}</td>
											<td>${b.issuedDate}</td>
											<td>${b.dueDate}</td>
											<td>${b.status}</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<!-- OVERDUE + RETURNED -->
		<div class="row g-4 mt-4">

			<div class="col-lg-6">
				<div class="card">
					<div class="card-header bg-danger text-white">
						<h5 class="mb-0">Overdue Books</h5>
					</div>

					<table class="table table-bordered table-striped mb-0">
						<thead>
							<tr>
								<th>#</th>
								<th>Book</th>
								<th>Issue Date</th>
								<th>Due Date</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty dashboardMetrics.overdueBooks}">
									<tr>
										<td colspan="5" class="text-center">No overdue books 🎉</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${dashboardMetrics.overdueBooks}" var="o"
										varStatus="i">
										<tr>
											<td>${i.count}</td>
											<td>${o.bookName}</td>
											<td>${o.issuedDate}</td>
											<td>${o.dueDate}</td>
										</tr>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
			</div>

			<div class="col-lg-6">
				<div class="card">
					<div class="card-header bg-success text-white">
						<h5 class="mb-0">Returned Books History</h5>
					</div>

					<table class="table table-bordered table-striped mb-0">
						<thead>
							<tr>
								<th>#</th>
								<th>Book</th>
								<th>Issued On</th>
								<th>Returned On</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty dashboardMetrics.returnedBooksHistory}">
									<tr>
										<td colspan="4" class="text-center">No returned books</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${dashboardMetrics.returnedBooksHistory}"
										var="r" varStatus="i">
										<tr>
											<td>${i.count}</td>
											<td>${r.bookName}</td>
											<td>${r.issuedDate}</td>
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

		<!-- UPCOMING RETURNS -->
		<div class="row g-4 mt-4">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header bg-warning">
						<h5 class="mb-0">Upcoming Returns (Due Soon)</h5>
					</div>

					<table class="table table-bordered table-striped mb-0">
						<thead>
							<tr>
								<th>#</th>
								<th>Book</th>
								<th>Due Date</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${empty dashboardMetrics.upcomingReturns}">
									<tr>
										<td colspan="4" class="text-center">No upcoming returns</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach items="${dashboardMetrics.upcomingReturns}" var="u"
										varStatus="i">
										<tr>
											<td>${i.count}</td>
											<td>${u.bookName}</td>
											<td>${u.dueDate}</td>
											<td>${u.status}</td>
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

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
