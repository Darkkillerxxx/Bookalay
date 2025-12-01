<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<%@ include file="parentDashboardCommon.jsp"%>
	</div>
</body>
</html>