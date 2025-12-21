<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>View Request</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


<style>
body {
	background: #f8f9fc;
}

.section-card {
	border-radius: 12px;
}

.label {
	font-weight: 600;
	color: #444;
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


<body class="p-4">

	<div class="toast-container position-fixed top-0 end-0 p-3">
	  <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
	    <div class="toast-header">
	      <strong class="me-auto">Library System</strong>
	      <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
	    </div>
	    <div class="toast-body">
	      <c:out value="${transaction.message}" />
	    </div>
	  </div>
	</div>

	<%@ include file="adminDashboardCommon.jsp"%>

	<div class="container-fluid mt-4">

		<div class="row">
			<div class="col-6">
				<h3 class="mb-3">Request Details</h3>

				<div class="card p-4 shadow section-card">

					<!-- ===================== Parent Details ===================== -->
					<h5 class="text-primary mb-3 border-bottom pb-2 mt-3">Parent
						Details</h5>

					<div class="row mb-3">
						<div class="col-md-6">
							<span class="label">Name</span> <br/>
							 <b>${requestDetail.parentName}</b>
						</div>
						<div class="col-md-6">
							<span class="label">Email:</span> <br/>
							<b>${requestDetail.parentEmail}</b> 
						</div>
						<div class="col-md-6 mt-2">
							<span class="label">Phone:</span><br/> 
							<b>${requestDetail.parentPhone}</b>
						</div>
					</div>

					<!-- ===================== Child Details ===================== -->
					<h5 class="text-success mb-3 border-bottom pb-2 mt-3">Child
						Details</h5>

					<div class="row mb-3">
						<div class="col-md-6">
							<span class="label">Name:</span><br/>
							<b>${requestDetail.childName}</b> 
						</div>
						<div class="col-md-6">
							<span class="label">Age:</span><br/> 
							<b>${requestDetail.childAge}</b>
						</div>
					</div>

					<!-- ===================== Book Details ===================== -->
					<h5 class="text-danger mb-3 border-bottom pb-2 mt-3">Book
						Details</h5>

					<div class="row mb-3">
						<div class="col-md-6">
							<span class="label">Book Name:</span><br/>
							<b>${requestDetail.bookName}</b> 
						</div>
						<div class="col-md-6">
							<span class="label">Genre:</span><br/>
							<b>${requestDetail.bookGenre}</b> 
						</div>
					</div>

					<div class="row mb-3">
						<div class="col-md-6">
							<span class="label">Requested On:</span> <br/>
							<b>${requestDetail.requestDate}</b>
						</div>
						<div class="col-md-6">
							<span class="label">Issued On:</span><br/>
							<b>${requestDetail.issuedDate}</b> 
						</div>
					</div>

					<div class="row mb-4">
						<div class="col-md-6">
							<span class="label">Due Date:</span><br/>
							<b>${requestDetail.dueDate}</b> 
						</div>

						<div class="col-md-6">
							<span class="label">Status :</span><br/> <span
								class="badge ${requestDetail.status == 'pending' ? 'bg-warning' : 
                                      requestDetail.status == 'approved' ? 'bg-info' :
                                      requestDetail.status == 'issued' ? 'bg-primary' :
                                      requestDetail.status == 'returned' ? 'bg-success' :
                                      requestDetail.status == 'rejected' ? 'bg-danger' :
                                      'bg-secondary'}">${requestDetail.status}</span>
						</div>
					</div>

					<c:if test="${requestDetail.status != 'pending'}">
						<span class="label">Notes:</span>
						<br />
						<b>${requestDetail.notes}</b>
					</c:if>

					<!-- ===================== Action ===================== -->
					<form action="RequestController" method="post">
						<!-- Approve (only when pending) -->
						<div class="row mt-4">

							<input type="hidden" name="requestId"
								value="${requestDetail.requestId}" />
								
							<input type="hidden" name="bookId"
								value="${requestDetail.bookId}" />

							<c:if test="${requestDetail.status eq 'pending'}">
								<div class="row mb-4">
									<div class="col-md-12">
										<span class="label">Notes:</span>
									</div>
									<textarea class="form-control" name="notes" rows="4" cols="50"> </textarea>
								</div>
							</c:if>

							<c:if test="${requestDetail.status eq 'pending'}">
								<div class="col">
									<button name="action" value="approveRequest"
										class="btn btn-success w-100">Approve Request</button>
								</div>
							</c:if>

							<!-- Reject (only when pending) -->
							<c:if test="${requestDetail.status eq 'pending'}">
								<div class="col">
									<button name="action" value="rejectRequest"
										class="btn btn-danger w-100">Reject Request</button>
								</div>
							</c:if>

							<!-- Issue (only when approved) -->
							<c:if test="${requestDetail.status eq 'approved'}">
								<div class="col">
									<button name="action" value="issueRequest" 
									class="btn btn-warning w-100"> Issue Book </button>
								</div>
							</c:if>
							
							<c:if test="${requestDetail.status eq 'issued'}">
								<div class="col">
									<button name="action" value="returnRequest" 
									class="btn btn-success w-100"> Return Book </button>
								</div>
							</c:if>
						</div>
					</form>


				</div>
			</div>

			<div class="col-6">
				<div class="section-card">
					<h5 class="mb-3 text-dark">Other Request For this Book</h5>

					<table class="table table-bordered table-striped align-middle">
						<thead class="table-light">
							<tr>
								<th>#</th>
								<th>Parent</th>
								<th>Book</th>
								<th>Requested On</th>
								<th>Approved On</th>
								<th>Issued On</th>
								<th>Status</th>
							</tr>
						</thead>

						<tbody>
							<c:choose>
								<c:when test="${empty requestList}">
									<tr>
										<td colspan="5" class="text-center text-muted">No
											requests found for this book</td>
									</tr>
								</c:when>

								<c:otherwise>
									<c:forEach items="${requestList}" var="r" varStatus="i">
										<tr>
											<td>${i.count}</td>
											<td>${r.parentName}</td>
											<td>${r.bookName}</td>
											<td>${r.requestDate}</td>
											<td>${r.approvalDate}</td>
											<td>${r.issuedDate}</td>
											<td><span
												class="badge 
                                    ${r.status == 'pending' ? 'bg-warning' : 
                                      r.status == 'approved' ? 'bg-info' :
                                      r.status == 'issued' ? 'bg-primary' :
                                      r.status == 'returned' ? 'bg-success' :
                                      'bg-secondary'}">
													${r.status} </span></td>
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
</body>
</html>
