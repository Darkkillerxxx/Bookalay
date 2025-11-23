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
		<div class="row">
			<div class="col-12">
				<!-- Users Waiting Approval -->
				<div class="card p-3 mt-3">
					<div
						class="card-header bg-warning text-dark d-flex justify-content-between align-items-center">
						<h5 class="mb-0">Users Waiting for Approval</h5>

						<!-- Search Form -->
						<form class="d-flex" method="get"
							action="ManageApprovalsController">
							<input type="hidden" name="action" value="manageApprovals" /> <input
								type="text" name="search" value="${search}"
								class="form-control me-2"
								placeholder="Search by name, username or email" />

							<button class="btn btn-dark" type="submit">Search</button>
						</form>
					</div>

					<div class="card-body p-0">
						<table class="table table-striped mb-0">
							<thead class="table-light">
								<tr>
									<th>#</th>
									<th>User Name</th>
									<th>Email</th>
									<th>Requested On</th>
									<th>Action</th>
								</tr>
							</thead>

							<tbody>
								<c:choose>
									<c:when test="${not empty usersForApproval}">
										<c:forEach var="p" items="${usersForApproval}"
											varStatus="loop">
											<tr>
												<td>${loop.index + 1}</td>
												<td>${p.parentName}</td>
												<td>${p.email}</td>
												<td>${p.registrationDate}</td>
												<td><a
													href="ManageApprovalsController?action=viewApproverDetail&userId=${p.parentId}"
													class="btn btn-primary btn-sm">View</a></td>
											</tr>
										</c:forEach>
									</c:when>

									<c:otherwise>
										<tr>
											<td colspan="5" class="text-center text-muted p-3">No
												users found.</td>
										</tr>
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