<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>View Request</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet" />

    <style>
        body { background:#f8f9fc; }
        .section-card { border-radius:12px; }
        .label { font-weight:600; color:#444; }
    </style>
</head>

<body class="p-4">

    <%@ include file="adminDashboardCommon.jsp" %>

    <div class="container mt-4">

        <h3 class="mb-3">Request Details</h3>

        <!-- ===================== Parent Details ===================== -->
        <div class="card p-3 mb-4 shadow section-card">
            <h5 class="text-primary mb-3">Parent Details</h5>

            <div class="row mb-2">
                <div class="col-md-6">
                    <span class="label">Name:</span> Rick Boulevard
                </div>
                <div class="col-md-6">
                    <span class="label">Email:</span> rickboulevard@mail.com
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <span class="label">Phone:</span> 9876543210
                </div>
            </div>
        </div>

        <!-- ===================== Child Details ===================== -->
        <div class="card p-3 mb-4 shadow section-card">
            <h5 class="text-success mb-3">Child Details</h5>

            <div class="row mb-2">
                <div class="col-md-6">
                    <span class="label">Name:</span> Trent Boulevard
                </div>
                <div class="col-md-6">
                    <span class="label">Class:</span> 2nd Grade
                </div>
            </div>
        </div>

        <!-- ===================== Book Details ===================== -->
        <div class="card p-3 mb-4 shadow section-card">
            <h5 class="text-danger mb-3">Book Details</h5>

            <div class="row mb-2">
                <div class="col-md-6">
                    <span class="label">Book Name:</span> Life of Pi
                </div>
                <div class="col-md-6">
                    <span class="label">Genre:</span> Fiction
                </div>
            </div>

            <div class="row mb-2">
                <div class="col-md-6">
                    <span class="label">Requested On:</span> 29-11-2025
                </div>

                <div class="col-md-6">
                    <span class="label">Issued On:</span> -
                </div>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <span class="label">Due Date:</span> -
                </div>
            </div>
        </div>
        <a href="RequestController?action=viewRequest" class="btn btn-secondary">Back</a>
    </div>
</body>
</html>
