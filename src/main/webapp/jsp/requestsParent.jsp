<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Bookalay - Your Requests</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<style>
body {
	background: #f8f9fc;
}

.card {
	border: none;
	border-radius: 15px;
}

.table td, .table th {
	vertical-align: middle;
}
</style>

<c:if test="${showToast}">
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            var toastEl = document.getElementById('liveToast');
            if (toastEl) {
                var toast = new bootstrap.Toast(toastEl);
                toast.show();
            }
        });
    </script>
</c:if>


</head>

<body>
	<div class="toast-container position-fixed top-0 end-0 p-3">
	  <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
	    <div class="toast-header">
	      <strong class="me-auto">Library System</strong>
	      <button type="button" class="btn-close" data-bs-dismiss="toast"></button>
	    </div>
	    <div class="toast-body">
	      <c:out value="${toastMessage}" />
	    </div>
	  </div>
	</div>
	<div class="container-fluid p-4">
		
		<%@ include file="parentDashboardCommon.jsp"%>

		<div class="row">
			<div class="col-12">

				<!-- Requests Table -->
				<div class="card p-3 mt-3">

					<div
						class="card-header bg-primary text-light d-flex justify-content-between align-items-center">
						<h5 class="mb-0">Your Book Requests</h5>
					</div>

					<div class="card-body p-0">

						<table class="table table-striped mb-0">
							<thead class="table-light">
								<tr>
									<th>#</th>
									<th>Book Name</th>
									<th>Requested Date</th>
									<th>Due Date</th>
									<th>Status</th>
									<th>Action</th>
								</tr>
							</thead>

							<tbody>

								<c:choose>

									<c:when test="${not empty requestList}">
										<c:forEach var="r" items="${requestList}" varStatus="loop">
											<tr>
												<!-- Index -->
												<td>${loop.index + 1}</td>

												<!-- Book Name -->
												<td>${r.bookName}</td>

												<!-- Requested Date -->
												<td><c:choose>
														<c:when test="${not empty r.requestDate}">
                                                        ${r.requestDate}
                                                    </c:when>
														<c:otherwise>
															<span class="text-muted">—</span>
														</c:otherwise>
													</c:choose></td>

												<!-- Due Date -->
												<td><c:choose>
														<c:when test="${not empty r.dueDate}">
                                                        ${r.dueDate}
                                                    </c:when>
														<c:otherwise>
															<span class="text-muted">—</span>
														</c:otherwise>
													</c:choose></td>

												<!-- Status -->
												<td><c:choose>

														<c:when test="${fn:toLowerCase(r.status) == 'pending'}">
															<span class="badge bg-warning text-dark">Pending</span>
														</c:when>

														<c:when test="${fn:toLowerCase(r.status) == 'approved'}">
															<span class="badge bg-success">Approved</span>
														</c:when>

														<c:when test="${fn:toLowerCase(r.status) == 'issued'}">
															<span class="badge bg-primary">Issued</span>
														</c:when>

														<c:when test="${fn:toLowerCase(r.status) == 'returned'}">
															<span class="badge bg-secondary">Returned</span>
														</c:when>

														<c:when test="${fn:toLowerCase(r.status) == 'rejected'}">
															<span class="badge bg-danger">Rejected</span>
														</c:when>

														<c:when test="${fn:toLowerCase(r.status) == 'overdue'}">
															<span class="badge bg-danger">Overdue</span>
														</c:when>

														<c:otherwise>
															<span class="badge bg-dark">${r.status}</span>
														</c:otherwise>

													</c:choose></td>
													
												<td>
													<form action="RequestController" method="get" class="m-0">
														<input type="hidden" name="action"
															value="viewSingleRequest" /> <input type="hidden"
															name="requestId" value="${r.requestId}" /> <a
															href="RequestController?action=requestDetailsParent&requestId=${r.requestId}"
															class="btn btn-sm btn-primary"> View Request </a>
													</form>
												</td>


											</tr>
										</c:forEach>
									</c:when>

									<c:otherwise>
										<tr>
											<td colspan="5" class="text-center text-muted p-3">No
												book requests found.</td>
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
