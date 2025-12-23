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

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>


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
	document.addEventListener("DOMContentLoaded", function() {
<%if (request.getAttribute("showToast") != null) {%>
	var toastEl = document.getElementById('liveToast');
		var toast = new bootstrap.Toast(toastEl);
		toast.show();
<%}%>
	});
</script>


<body class="p-4">

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

	<div class="container-fluid mt-4">

		<div class="row">
			<div class="col-12">
				<h3 class="mb-3">Request Details</h3>

				<div class="card p-4 shadow section-card">

					<!-- ===================== Parent Details ===================== -->
					<h5 class="text-primary mb-3 border-bottom pb-2 mt-3">Parent
						Details</h5>

					<div class="row mb-3">
						<div class="col-md-6">
							<span class="label">Name</span> <br /> <b>${requestDetail.parentName}</b>
						</div>
						<div class="col-md-6">
							<span class="label">Email:</span> <br /> <b>${requestDetail.parentEmail}</b>
						</div>
						<div class="col-md-6 mt-2">
							<span class="label">Phone:</span><br /> <b>${requestDetail.parentPhone}</b>
						</div>
					</div>

					<!-- ===================== Child Details ===================== -->
					<h5 class="text-success mb-3 border-bottom pb-2 mt-3">Child
						Details</h5>

					<div class="row mb-3">
						<div class="col-md-6">
							<span class="label">Name:</span><br /> <b>${requestDetail.childName}</b>
						</div>
						<div class="col-md-6">
							<span class="label">Age:</span><br /> <b>${requestDetail.childAge}</b>
						</div>
					</div>

					<!-- ===================== Book Details ===================== -->
					<h5 class="text-danger mb-3 border-bottom pb-2 mt-3">Book
						Details</h5>

					<div class="row mb-3">
						<div class="col-md-6">
							<span class="label">Book Name:</span><br /> <b>${requestDetail.bookName}</b>
						</div>
						<div class="col-md-6">
							<span class="label">Genre:</span><br /> <b>${requestDetail.bookGenre}</b>
						</div>
					</div>

					<div class="row mb-3">
						<div class="col-md-6">
							<span class="label">Requested On:</span> <br /> <b>${requestDetail.requestDate}</b>
						</div>
						<div class="col-md-6">
							<span class="label">Issued On:</span><br /> <b>${requestDetail.issuedDate}</b>
						</div>
					</div>

					<div class="row mb-4">
						<div class="col-md-6">
							<span class="label">Due Date:</span><br /> <b>${requestDetail.dueDate}</b>
						</div>

						<div class="col-md-6">
							<span class="label">Status :</span><br /> <span
								class="badge ${requestDetail.status == 'pending' ? 'bg-warning' : 
                                      requestDetail.status == 'approved' ? 'bg-info' :
                                      requestDetail.status == 'issued' ? 'bg-primary' :
                                      requestDetail.status == 'returned' ? 'bg-success' :
                                      requestDetail.status == 'rejected' ? 'bg-danger' :
                                      'bg-secondary'}">${requestDetail.status}</span>
						</div>
					</div>

					<span class="label">Notes:</span> <br /> <b>${requestDetail.notes}</b>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
