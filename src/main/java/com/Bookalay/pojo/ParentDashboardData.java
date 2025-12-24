package com.Bookalay.pojo;

import java.util.List;

public class ParentDashboardData {

    // ----------- METRICS -----------
    private int totalBooksRequested;
    private int booksCurrentlyIssued;
    private int booksOverdue;
    private int dueSoon;
    private int booksReturned;

    // ----------- TABLES -----------
    private List<Request> recentBookRequests;
    private List<Request> booksCurrentlyIssuedList;
    private List<Request> overdueBooks;
    private List<Request> lateReturns;
    private List<Request> returnedBooksHistory;
    private List<Request> upcomingReturns;

    // ----------- GETTERS & SETTERS -----------

    public int getTotalBooksRequested() {
        return totalBooksRequested;
    }

    public void setTotalBooksRequested(int totalBooksRequested) {
        this.totalBooksRequested = totalBooksRequested;
    }

    public int getBooksCurrentlyIssued() {
        return booksCurrentlyIssued;
    }

    public void setBooksCurrentlyIssued(int booksCurrentlyIssued) {
        this.booksCurrentlyIssued = booksCurrentlyIssued;
    }

    public int getBooksOverdue() {
        return booksOverdue;
    }

    public void setBooksOverdue(int booksOverdue) {
        this.booksOverdue = booksOverdue;
    }

    public int getDueSoon() {
        return dueSoon;
    }

    public void setDueSoon(int dueSoon) {
        this.dueSoon = dueSoon;
    }

    public int getBooksReturned() {
        return booksReturned;
    }

    public void setBooksReturned(int booksReturned) {
        this.booksReturned = booksReturned;
    }

    public List<Request> getRecentBookRequests() {
        return recentBookRequests;
    }

    public void setRecentBookRequests(List<Request> recentBookRequests) {
        this.recentBookRequests = recentBookRequests;
    }

    public List<Request> getBooksCurrentlyIssuedList() {
        return booksCurrentlyIssuedList;
    }

    public void setBooksCurrentlyIssuedList(List<Request> booksCurrentlyIssuedList) {
        this.booksCurrentlyIssuedList = booksCurrentlyIssuedList;
    }

    public List<Request> getOverdueBooks() {
        return overdueBooks;
    }

    public void setOverdueBooks(List<Request> overdueBooks) {
        this.overdueBooks = overdueBooks;
    }

    public List<Request> getReturnedBooksHistory() {
        return returnedBooksHistory;
    }

    public void setReturnedBooksHistory(List<Request> returnedBooksHistory) {
        this.returnedBooksHistory = returnedBooksHistory;
    }

    public List<Request> getUpcomingReturns() {
        return upcomingReturns;
    }

    public void setUpcomingReturns(List<Request> upcomingReturns) {
        this.upcomingReturns = upcomingReturns;
    }

    // ----------- INNER POJO CLASSES -----------


    // 2. BOOKS CURRENTLY ISSUED
    public static class IssuedBook {
        private String bookName;
        private String issueDate;
        private String dueDate;
        private String status; // Overdue, Due Soon, On Time

        public String getBookName() { return bookName; }
        public void setBookName(String bookName) { this.bookName = bookName; }

        public String getIssueDate() { return issueDate; }
        public void setIssueDate(String issueDate) { this.issueDate = issueDate; }

        public String getDueDate() { return dueDate; }
        public void setDueDate(String dueDate) { this.dueDate = dueDate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    // 3. OVERDUE BOOKS
    public static class OverdueBook {
        private String bookName;
        private String issueDate;
        private String dueDate;
        private String daysOverdue;

        public String getBookName() { return bookName; }
        public void setBookName(String bookName) { this.bookName = bookName; }

        public String getIssueDate() { return issueDate; }
        public void setIssueDate(String issueDate) { this.issueDate = issueDate; }

        public String getDueDate() { return dueDate; }
        public void setDueDate(String dueDate) { this.dueDate = dueDate; }

        public String getDaysOverdue() { return daysOverdue; }
        public void setDaysOverdue(String daysOverdue) { this.daysOverdue = daysOverdue; }
    }


    
    @Override
    public String toString() {
        return "ParentDashboardData{" +
                "totalBooksRequested=" + totalBooksRequested +
                ", booksCurrentlyIssued=" + booksCurrentlyIssued +
                ", booksOverdue=" + booksOverdue +
                ", booksReturned=" + booksReturned +
                '}';
    }

	public List<Request> getLateReturns() {
		return lateReturns;
	}

	public void setLateReturns(List<Request> lateReturns) {
		this.lateReturns = lateReturns;
	}
}
