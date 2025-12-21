<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Child Registration | Library</title>

<!-- Bootstrap -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

<!-- Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=Playfair+Display:wght@400;500;600&display=swap" rel="stylesheet">

<style>
    body {
        background: #f3f0ff;
        font-family: 'Inter', sans-serif;
    }
    .main-title {
        font-size: 32px;
        font-weight: 700;
        color: #7a3cff;
    }
    .section-title {
        font-weight: 600;
        font-size: 18px;
        color: #5b2ecc;
    }
    .card-custom {
        border-radius: 20px;
        border: none;
        box-shadow: 0 4px 20px rgba(0,0,0,0.08);
        padding: 20px 30px;
        background: #ffffffcc;
        backdrop-filter: blur(6px);
    }
    .divider {
        height: 1px;
        background: #e5e1ff;
        margin: 25px 0;
    }
    .btn-purple {
        background: linear-gradient(to right, #923bff, #3f5efb);
        border: none;
        padding: 12px;
        font-weight: 600;
        color: white;
        width: 100%;
        border-radius: 8px;
    }
</style>

<script type="text/javascript">
	document.addEventListener("DOMContentLoaded", function () {
	    <% if (request.getAttribute("showToast") != null) { %>
	        var toastEl = document.getElementById('liveToast');
	        var toast = new bootstrap.Toast(toastEl);
	        toast.show();
	    <% } %>
	});
</script>

</head>

<body>
	<div class="toast-container position-fixed top-0 end-0 p-3">
	  <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
	    <div class="toast-header">
	      <strong class="me-auto">Library System</strong>
	      <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
	    </div>
	    <div class="toast-body">
	      <c:out value="${toastMessage}" />
	    </div>
	  </div>
	</div>

	<form action="RegisterController" method="post"> 
	    <input type="hidden" name="action" value="register"/>
	
		<div class="container py-5">
		    <h2 class="text-center main-title">Join Our Reading Community</h2>
		    <p class="text-center text-muted mb-4">
		        Register your child to start their exciting reading journey
		    </p>
		    
		 
		
		    <div class="card card-custom mx-auto" style="max-width: 850px;padding:0px">
			    <c:if test="${not empty errorMessage}">
					<div class="alert alert-danger alert-dismissible fade show" role="alert">
						<c:out value="${errorMessage}"/>
						<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
					</div>
			    </c:if>
			    
		        <!-- Child Information -->
		        <div class="mb-3">
		            
		            <div style="background-color:#f5f5ff">
						<h5 class="section-title p-3">
			                <i class="bi bi-person-circle me-2"></i> Child Information
			            </h5>
		            </div>
		
		            <div class="row g-3 p-3">
		                <div class="col-md-6">
		                    <label class="form-label">Child’s Full Name *</label>
		                    <input type="text" class="form-control" name="childName" placeholder="Enter child's name" required>
		                </div>
		                <div class="col-md-6">
		                    <label class="form-label">Age *</label>
		                    <select class="form-select" name="age" required>
		                        <option value="">Select age</option>
		                        <option>5</option>
		                        <option>6</option>
		                        <option>7</option>
		                    </select>
		                </div>
		                <div class="col-md-6">
		                    <label class="form-label">Gender *</label>
		                    <select class="form-select" name="gender" required>
		                        <option value="">Select Gender</option>
		                        <option>Boy</option>
		                        <option>Girl</option>
		                    </select>
		                </div>
		            </div>
		
		            <div class="mt-3 p-3">
		                <label class="form-label">Interests * (Select all that apply)</label>
		                <div class="row">
		                    <div class="col-md-4"><input name="interests" value="Animals" type="checkbox"> Animals</div>
		                    <div class="col-md-4"><input name="interests" value="Sports" type="checkbox"> Sports</div>
		                    <div class="col-md-4"><input name="interests" value="Science" type="checkbox"> Science</div>
		                    <div class="col-md-4"><input name="interests" value="Art" type="checkbox"> Art</div>
		                    <div class="col-md-4"><input name="interests" value="Music" type="checkbox"> Music</div>
		                    <div class="col-md-4"><input name="interests" value="Technology" type="checkbox"> Technology</div>
		                    <div class="col-md-4"><input name="interests" value="Nature" type="checkbox"> Nature</div>
		                    <div class="col-md-4"><input name="interests" value="Adventure" type="checkbox"> Adventure</div>
		                </div>
		            </div>
		        </div>
		
		        <div class="divider mb-0"></div>
		
		        <!-- Parent Contact Information -->
		        <div class="mb-3">
					<div style="background-color:#f5f5ff">
						<h5 class="section-title p-3">
			                <i class="bi bi-person-check me-2"></i> Parent Contact Information
			            </h5>
					</div>
		            
		
		            <div class="row g-3 p-3">
		                <div class="col-md-6">
		                    <label class="form-label">Parent/Guardian Name *</label>
		                    <input type="text" class="form-control" name="parentName" placeholder="Enter your name" required>
		                </div>
		                <div class="col-md-6">
		                    <label class="form-label">Phone Number</label>
		                    <input type="text" class="form-control" name="phone" placeholder="(123) 456-7890">
		                </div>
		                <div class="col-md-6">
		                    <label class="form-label">Email Address *</label>
		                    <input type="email" class="form-control" name="email" placeholder="your@email.com" required>
		                </div>
		            </div>
		        </div>
				
				
				<div class="divider mb-0"></div>
				                
		        <!-- Parent User Setup -->
				<div class="mb-3">
				    <div style="background-color:#f5f5ff">
				        <h5 class="section-title p-3">
				            <i class="bi bi-person-gear me-2"></i> Parent User Setup
				        </h5>
				    </div>
				
				    <div class="row g-3 p-3">
				        <div class="col-md-6">
				            <label class="form-label">Username *</label>
				            <input type="text" class="form-control" name="username" placeholder="Enter username" required>
				        </div>
				
				        <div class="col-md-6">
				            <label class="form-label">Password *</label>
				            <input type="password" class="form-control" type="Password" name="password" placeholder="Enter password" required>
				        </div>
				
				        <div class="col-md-6">
				            <label class="form-label">Re-enter Password *</label>
				            <input type="password" class="form-control" type="Password" name="reEnteredPassword" placeholder="Re-enter password" required>
				        </div>
				    </div>
				</div>
		
		
		        <div class="divider mb-0"></div>
		
		        <!-- Reading Preferences -->
		        <div>
		        	<div style="background-color:#f5f5ff">        
			            <h5 class="section-title p-3">
			                <i class="bi bi-book-heart me-2"></i> Reading Preferences
			            </h5>
			        </div>
					
					<div class="p-3">
			            <label class="form-label mt-2">Current Reading Level</label>
			            <select class="form-select mb-3" name="readingLevel">
			                <option value="">Select reading level</option>
			                <option>Beginner</option>
			                <option>Intermediate</option>
			                <option>Expert</option>
			            </select>
			
			            <label class="form-label">Favorite Genres * (Select all that apply)</label>
			            <div class="row mb-3">
			                <div class="col-md-4"><input name="genres" value="Fiction" type="checkbox"> Fiction</div>
			                <div class="col-md-4"><input name="Genres" value="Non-fiction" type="checkbox"> Non-fiction</div>
			                <div class="col-md-4"><input name="Genres" value="Fantasy" type="checkbox"> Fantasy</div>
			                <div class="col-md-4"><input name="Genres" value="Mystery" type="checkbox"> Mystery</div>
			                <div class="col-md-4"><input name="Genres" value="Science Fiction" type="checkbox"> Science Fiction</div>
			                <div class="col-md-4"><input name="Genres" value="Biography" type="checkbox"> Biography</div>
			                <div class="col-md-4"><input name="Genres" value="Comics" type="checkbox"> Comics</div>
			                <div class="col-md-4"><input name="Genres" value="Poetry" type="checkbox"> Poetry</div>
			            </div>
			
			            <label class="form-label">Reading Frequency</label>
			            <select class="form-select mb-3" name="readingFrequency">
			                <option value="">Select Reading frequency</option>
			                <option>4–6 times a week</option>
			                <option>2–3 times a week</option>
			                <option>Once a week</option>
			                <option>Once or twice a month</option>
			                <option>Rarely</option>    
			            </select>
			            
			            <label class="form-label">Additional Notes</label>
			            <textarea class="form-control" rows="3" name="notes" placeholder="Any specific preferences or learning goals..."></textarea>
		            </div>
		        </div>
		        
		        <!-- Submit -->
		        <div class="p-3">
		            <button class="btn btn-purple">Complete Registration</button>
		        </div>
		
		        <p class="text-center text-muted small mt-3">
		            By registering, you agree to receive personalized book recommendations and reading resources.
		        </p>
		    </div>		
		</div>
	</form>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
