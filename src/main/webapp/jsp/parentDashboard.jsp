<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Parent Dashboard</title>

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

.badge-status {
	font-size: 0.8rem;
}
</style>
</head>

<body>

	<div class="container-fluid p-4">

		<%@ include file="parentDashboardCommon.jsp"%>

		<!-- TOP METRICS ROW -->
		<div class="row g-4 mt-2">

			<div class="col">
				<div class="card p-3 text-center">
					<h4 class="fw-bold">12</h4>
					<p class="text-muted small">Total Books Requested</p>
				</div>
			</div>

			<div class="col">
				<div class="card p-3 text-center">
					<h4 class="fw-bold">5</h4>
					<p class="text-muted small">Books Currently Issued</p>
				</div>
			</div>

			<div class="col">
				<div class="card p-3 text-center">
					<h4 class="fw-bold">2</h4>
					<p class="text-muted small">Books Overdue</p>
				</div>
			</div>

			<div class="col">
				<div class="card p-3 text-center">
					<h4 class="fw-bold">3</h4>
					<p class="text-muted small">Due Soon</p>
				</div>
			</div>

			<div class="col">
				<div class="card p-3 text-center">
					<h4 class="fw-bold">18</h4>
					<p class="text-muted small">Books Returned</p>
				</div>
			</div>
		</div>
		<!-- END METRICS ROW -->


		<!-- FIRST ROW -->
		<div class="row g-4 mt-4">

			<!-- Recent Requests -->
			<div class="col-lg-6">
				<div class="card p-3">
					<div class="card-header bg-white">
						<h5 class="mb-0">Recent Book Requests</h5>
					</div>

					<div class="card-body p-0">
						<table class="table table-striped table-bordered mb-0">
							<thead class="table-light">
								<tr>
									<th>#</th>
									<th>Book Name</th>
									<th>Requested Date</th>
									<th>Status</th>
								</tr>
							</thead>

							<tbody>
								<tr>
									<td>1</td>
									<td>Atomic Habits</td>
									<td>12 Jan 2025</td>
									<td><span class="badge bg-warning text-dark">Pending</span></td>
								</tr>

								<tr>
									<td>2</td>
									<td>The Alchemist</td>
									<td>10 Jan 2025</td>
									<td><span class="badge bg-success">Approved</span></td>
								</tr>

								<tr>
									<td>3</td>
									<td>Rich Dad Poor Dad</td>
									<td>09 Jan 2025</td>
									<td><span class="badge bg-danger">Rejected</span></td>
								</tr>
							</tbody>

						</table>
					</div>
				</div>
			</div>

			<!-- Books Currently Issued -->
			<div class="col-lg-6">
				<div class="card p-3">
					<div class="card-header bg-primary text-white">
						<h5 class="mb-0">Books Currently Issued</h5>
					</div>

					<div class="card-body p-0">
						<table class="table table-striped table-bordered mb-0">
							<thead class="table-light">
								<tr>
									<th>#</th>
									<th>Book</th>
									<th>Issue Date</th>
									<th>Due Date</th>
									<th>Status</th>
								</tr>
							</thead>

							<tbody>
								<tr>
									<td>1</td>
									<td>The Great Gatsby</td>
									<td>02 Jan 2025</td>
									<td>10 Jan 2025</td>
									<td><span class="badge bg-danger">Overdue</span></td>
								</tr>

								<tr>
									<td>2</td>
									<td>Harry Potter</td>
									<td>05 Jan 2025</td>
									<td>14 Jan 2025</td>
									<td><span class="badge bg-warning text-dark">Due
											Soon</span></td>
								</tr>

								<tr>
									<td>3</td>
									<td>Think Like a Monk</td>
									<td>08 Jan 2025</td>
									<td>15 Jan 2025</td>
									<td><span class="badge bg-success">On Time</span></td>
								</tr>
							</tbody>

						</table>
					</div>
				</div>
			</div>

		</div>
		<!-- END FIRST ROW -->


		<!-- SECOND ROW -->
		<div class="row g-4 mt-2">

			<!-- Overdue Books -->
			<div class="col-lg-6">
				<div class="card p-3">
					<div class="card-header bg-danger text-white">
						<h5 class="mb-0">Overdue Books</h5>
					</div>

					<div class="card-body p-0">
						<table class="table table-striped table-bordered mb-0">
							<thead class="table-light">
								<tr>
									<th>#</th>
									<th>Book Title</th>
									<th>Issue Date</th>
									<th>Due Date</th>
									<th>Days Overdue</th>
								</tr>
							</thead>

							<tbody>
								<tr>
									<td>1</td>
									<td>The Great Gatsby</td>
									<td>02 Jan 2025</td>
									<td>10 Jan 2025</td>
									<td>3 Days</td>
								</tr>

								<tr>
									<td>2</td>
									<td>Wings of Fire</td>
									<td>01 Jan 2025</td>
									<td>08 Jan 2025</td>
									<td>5 Days</td>
								</tr>
							</tbody>

						</table>
					</div>
				</div>
			</div>

			<!-- Returned Books History -->
			<div class="col-lg-6">
				<div class="card p-3">
					<div class="card-header bg-success text-white">
						<h5 class="mb-0">Returned Books History</h5>
					</div>

					<div class="card-body p-0">
						<table class="table table-striped table-bordered mb-0">
							<thead class="table-light">
								<tr>
									<th>#</th>
									<th>Book Title</th>
									<th>Issued On</th>
									<th>Returned On</th>
								</tr>
							</thead>

							<tbody>
								<tr>
									<td>1</td>
									<td>Do Epic Shit</td>
									<td>10 Dec 2024</td>
									<td>17 Dec 2024</td>
								</tr>

								<tr>
									<td>2</td>
									<td>The Secret</td>
									<td>15 Dec 2024</td>
									<td>22 Dec 2024</td>
								</tr>

							</tbody>

						</table>
					</div>
				</div>
			</div>

		</div>
		<!-- END SECOND ROW -->


		<!-- THIRD ROW -->
		<div class="row g-4 mt-2">

			<!-- Upcoming Returns -->
			<div class="col-lg-12">
				<div class="card p-3 shadow-sm">
					<div class="card-header bg-warning text-dark">
						<h5 class="mb-0">Upcoming Returns (Due Soon)</h5>
					</div>

					<div class="card-body p-0">
						<div class="table-responsive">
							<table class="table table-striped table-bordered mb-0">
								<thead class="table-light">
									<tr>
										<th>#</th>
										<th>Book</th>
										<th>Due Date</th>
										<th>Status</th>
									</tr>
								</thead>

								<tbody>

									<tr>
										<td>1</td>
										<td>Harry Potter</td>
										<td>14 Jan 2025</td>
										<td><span class="badge bg-warning text-dark">Due
												Tomorrow</span></td>
									</tr>

									<tr>
										<td>2</td>
										<td>Think Like a Monk</td>
										<td>15 Jan 2025</td>
										<td><span class="badge bg-info text-dark">Due Soon</span></td>
									</tr>

								</tbody>
							</table>
						</div>
					</div>

				</div>
			</div>

		</div>
		<!-- END THIRD ROW -->


	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
