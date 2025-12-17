package com.Bookalay.pojo;

public class Request {

    // ===== Existing fields =====
    private int requestId;
    private int parentId;
    private String parentName;
    private int childId;
    private int bookId;
    private String bookName;
    private String status;
    private String requestDate;
    private String approvalDate;
    private String issuedDate;
    private String dueDate;
    private String returnedDate;
    private String notes;
    private Integer approvedBy;
    private Integer requestDuration;

    // ===== Parent fields (from parent table) =====
    private String parentEmail;
    private String parentPhone;

    // ===== Child fields (from child table) =====
    private String childName;
    private int childAge;
    private String childGender;
    private String childInterests;
    private String childReadingLevel;
    private String childGenres;
    private String childReadingFrequency;
    private String childNotes;

    // ===== Book fields (from book table) =====
    private String bookAuthor;
    private String bookInterest;
    private String bookGenre;
    private String bookDifficulty;
    private String seriesName;
    private int pageCount;
    private String summary;
    private String coverArt;
    private int isAvailable;
    private int totalCopies;
    private int availableCopies;
    private String dateAdded;

    // ===== User fields (from user table) =====
    private String username;
    private String userType;
    private String lastLogin;
    private int userActive;
    private String userCreatedDate;
    private int userApproved;

    public Request() {}

    // ===== Getters & Setters =====
    public int getRequestId() { return requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }

    public int getParentId() { return parentId; }
    public void setParentId(int parentId) { this.parentId = parentId; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }

    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }

    public int getChildId() { return childId; }
    public void setChildId(int childId) { this.childId = childId; }

    public String getChildName() { return childName; }
    public void setChildName(String childName) { this.childName = childName; }

    public int getChildAge() { return childAge; }
    public void setChildAge(int childAge) { this.childAge = childAge; }

    public String getChildGender() { return childGender; }
    public void setChildGender(String childGender) { this.childGender = childGender; }

    public String getChildInterests() { return childInterests; }
    public void setChildInterests(String childInterests) { this.childInterests = childInterests; }

    public String getChildReadingLevel() { return childReadingLevel; }
    public void setChildReadingLevel(String childReadingLevel) { this.childReadingLevel = childReadingLevel; }

    public String getChildGenres() { return childGenres; }
    public void setChildGenres(String childGenres) { this.childGenres = childGenres; }

    public String getChildReadingFrequency() { return childReadingFrequency; }
    public void setChildReadingFrequency(String childReadingFrequency) { this.childReadingFrequency = childReadingFrequency; }

    public String getChildNotes() { return childNotes; }
    public void setChildNotes(String childNotes) { this.childNotes = childNotes; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getBookAuthor() { return bookAuthor; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }

    public String getBookInterest() { return bookInterest; }
    public void setBookInterest(String bookInterest) { this.bookInterest = bookInterest; }

    public String getBookGenre() { return bookGenre; }
    public void setBookGenre(String bookGenre) { this.bookGenre = bookGenre; }

    public String getBookDifficulty() { return bookDifficulty; }
    public void setBookDifficulty(String bookDifficulty) { this.bookDifficulty = bookDifficulty; }

    public String getSeriesName() { return seriesName; }
    public void setSeriesName(String seriesName) { this.seriesName = seriesName; }

    public int getPageCount() { return pageCount; }
    public void setPageCount(int pageCount) { this.pageCount = pageCount; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getCoverArt() { return coverArt; }
    public void setCoverArt(String coverArt) { this.coverArt = coverArt; }

    public int getIsAvailable() { return isAvailable; }
    public void setIsAvailable(int isAvailable) { this.isAvailable = isAvailable; }

    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }

    public String getDateAdded() { return dateAdded; }
    public void setDateAdded(String dateAdded) { this.dateAdded = dateAdded; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRequestDate() { return requestDate; }
    public void setRequestDate(String requestDate) { this.requestDate = requestDate; }

    public String getApprovalDate() { return approvalDate; }
    public void setApprovalDate(String approvalDate) { this.approvalDate = approvalDate; }

    public String getIssuedDate() { return issuedDate; }
    public void setIssuedDate(String issuedDate) { this.issuedDate = issuedDate; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }

    public String getReturnedDate() { return returnedDate; }
    public void setReturnedDate(String returnedDate) { this.returnedDate = returnedDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Integer getApprovedBy() { return approvedBy; }
    public void setApprovedBy(Integer approvedBy) { this.approvedBy = approvedBy; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getLastLogin() { return lastLogin; }
    public void setLastLogin(String lastLogin) { this.lastLogin = lastLogin; }

    public int getUserActive() { return userActive; }
    public void setUserActive(int userActive) { this.userActive = userActive; }

    public String getUserCreatedDate() { return userCreatedDate; }
    public void setUserCreatedDate(String userCreatedDate) { this.userCreatedDate = userCreatedDate; }

    public int getUserApproved() { return userApproved; }
    public void setUserApproved(int userApproved) { this.userApproved = userApproved; }

	public Integer getRequestDuration() {
		return requestDuration;
	}

	public void setRequestDuration(Integer requestDuration) {
		this.requestDuration = requestDuration;
	}

}
