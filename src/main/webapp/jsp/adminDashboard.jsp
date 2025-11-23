<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Bookalay Dashboard</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
  <style>
    body { background: #f8f9fc; }
    .card { border: none; border-radius: 15px; }
    .nav-link.active { font-weight: bold; border-bottom: 2px solid #6f42c1; }
    .chart-placeholder { height: 180px; background: #eef0f7; border-radius: 10px; }
  </style>
</head>
<body>

<div class="container-fluid p-4">
 <%@ include file="adminDashboardCommon.jsp" %>

  <!-- Top Row -->
  <div class="row g-4">
    <!-- Left Card -->
    <div class="col-lg-6">
      <div class="card p-4 h-100">
        <h5 class="fw-bold">Website Audience Metrics</h5>
        <p class="text-muted small">Audience to which the users belonged while on the current date range.</p>

        <div class="row text-center mb-4">
          <div class="col">
            <h4 class="fw-bold">13,956</h4>
            <p class="text-muted small">Users</p>
          </div>
          <div class="col">
            <h4 class="fw-bold">3350</h4>
            <p class="text-muted small">Books Issued</p>
          </div>
          <div class="col">
            <h4 class="fw-bold">83,123</h4>
            <p class="text-muted small">Books Read</p>
          </div>
          <div class="col">
            <h4 class="fw-bold">16,869</h4>
            <p class="text-muted small">Users Waiting for Approval</p>
          </div>
        </div>

        <div class="row text-center mb-4">
          <div class="col">
            <h4 class="fw-bold">15</h4>
            <p class="text-muted small">In-Active Users</p>
          </div>
          <div class="col">
            <h4 class="fw-bold">335</h4>
            <p class="text-muted small">Overdue Books</p>
          </div>
          <div class="col">
            <h4 class="fw-bold">83,123</h4>
            <p class="text-muted small">Total Books Available</p>
          </div>
          <div class="col">
            <h4 class="fw-bold">83</h4>
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
                <th>Status</th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td>1</td>
                <td>The Great Gatsby</td>
                <td>Rahul Sharma</td>
                <td>2025-01-12</td>
                <td>2025-01-19</td>
                <td>Returned</td>
              </tr>
              <tr>
                <td>2</td>
                <td>Atomic Habits</td>
                <td>Pooja Singh</td>
                <td>2025-01-15</td>
                <td>2025-01-22</td>
                <td>Due Soon</td>
              </tr>
              <tr>
                <td>3</td>
                <td>The Psychology of Money</td>
                <td>Aditya Verma</td>
                <td>2025-01-10</td>
                <td>2025-01-17</td>
                <td>Overdue</td>
              </tr>
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
                <th>Requested On</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td>1</td>
                <td>John Doe</td>
                <td>john@example.com</td>
                <td>2025-11-18</td>
                <td>
                  <button class="btn btn-success btn-sm">Approve</button>
                  <button class="btn btn-danger btn-sm">Reject</button>
                </td>
              </tr>

              <tr>
                <td>2</td>
                <td>Asha Patil</td>
                <td>asha@example.com</td>
                <td>2025-11-17</td>
                <td>
                  <button class="btn btn-success btn-sm">Approve</button>
                  <button class="btn btn-danger btn-sm">Reject</button>
                </td>
              </tr>

              <tr>
                <td>3</td>
                <td>Asha Patil</td>
                <td>asha@example.com</td>
                <td>2025-11-17</td>
                <td>
                  <button class="btn btn-success btn-sm">Approve</button>
                  <button class="btn btn-danger btn-sm">Reject</button>
                </td>
              </tr>

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
                <th>Days Overdue</th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td>1</td>
                <td>The Great Gatsby</td>
                <td>Rahul Sharma</td>
                <td>2025-01-01</td>
                <td>2025-01-08</td>
                <td>10 Days</td>
              </tr>
              <tr>
                <td>2</td>
                <td>Atomic Habits</td>
                <td>Pooja Singh</td>
                <td>2025-01-05</td>
                <td>2025-01-12</td>
                <td>6 Days</td>
              </tr>
              <tr>
                <td>3</td>
                <td>The Psychology of Money</td>
                <td>Aditya Verma</td>
                <td>2025-01-03</td>
                <td>2025-01-10</td>
                <td>8 Days</td>
              </tr>
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
                  <th>Status</th>
                </tr>
              </thead>

              <tbody>
                <tr>
                  <td>1</td>
                  <td>Rahul Sharma</td>
                  <td>The Alchemist</td>
                  <td>18 Nov 2025</td>
                  <td><span class="badge bg-success">Due Today</span></td>
                </tr>

                <tr>
                  <td>2</td>
                  <td>Meera K</td>
                  <td>Rich Dad Poor Dad</td>
                  <td>18 Nov 2025</td>
                  <td><span class="badge bg-warning text-dark">Pending</span></td>
                </tr>

                <tr>
                  <td>3</td>
                  <td>Aditya Verma</td>
                  <td>Atomic Habits</td>
                  <td>18 Nov 2025</td>
                  <td><span class="badge bg-danger">Overdue</span></td>
                </tr>

              </tbody>

            </table>
          </div>
        </div>

      </div>
    </div>

  </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
