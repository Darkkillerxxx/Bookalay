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
    private List<RecentRequest> recentBookRequests;
    private List<IssuedBook> booksCurrentlyIssuedList;
    private List<OverdueBook> overdueBooks;
    private List<ReturnedBookHistory> returnedBooksHistory;
    private List<UpcomingReturn> upcomingReturns;

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

    public List<RecentRequest> getRecentBookRequests() {
        return recentBookRequests;
    }

    public void setRecentBookRequests(List<RecentRequest> recentBookRequests) {
        this.recentBookRequests = recentBookRequests;
    }

    public List<IssuedBook> getBooksCurrentlyIssuedList() {
        return booksCurrentlyIssuedList;
    }

    public void setBooksCurrentlyIssuedList(List<IssuedBook> booksCurrentlyIssuedList) {
        this.booksCurrentlyIssuedList = booksCurrentlyIssuedList;
    }

    public List<OverdueBook> getOverdueBooks() {
        return overdueBooks;
    }

    public void setOverdueBooks(List<OverdueBook> overdueBooks) {
        this.overdueBooks = overdueBooks;
    }

    public List<ReturnedBookHistory> getReturnedBooksHistory() {
        return returnedBooksHistory;
    }

    public void setReturnedBooksHistory(List<ReturnedBookHistory> returnedBooksHistory) {
        this.returnedBooksHistory = returnedBooksHistory;
    }

    public List<UpcomingReturn> getUpcomingReturns() {
        return upcomingReturns;
    }

    public void setUpcomingReturns(List<UpcomingReturn> upcomingReturns) {
        this.upcomingReturns = upcomingReturns;
    }

    // ----------- INNER POJO CLASSES -----------

    // 1. RECENT REQUESTS
    public static class RecentRequest {
        private String bookName;
        private String requestedDate;
        private String status; // Pending, Approved, Rejected

        // Getters & Setters
        public String getBookName() { return bookName; }
        public void setBookName(String bookName) { this.bookName = bookName; }

        public String getRequestedDate() { return requestedDate; }
        public void setRequestedDate(String requestedDate) { this.requestedDate = requestedDate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

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

    // 4. RETURNED HISTORY
    public static class ReturnedBookHistory {
        private String bookName;
        private String issuedOn;
        private String returnedOn;

        public String getBookName() { return bookName; }
        public void setBookName(String bookName) { this.bookName = bookName; }

        public String getIssuedOn() { return issuedOn; }
        public void setIssuedOn(String issuedOn) { this.issuedOn = issuedOn; }

        public String getReturnedOn() { return returnedOn; }
        public void setReturnedOn(String returnedOn) { this.returnedOn = returnedOn; }
    }

    // 5. UPCOMING RETURNS
    public static class UpcomingReturn {
        private String bookName;
        private String dueDate;
        private String status; // Due Tomorrow, Due Soon etc.

        public String getBookName() { return bookName; }
        public void setBookName(String bookName) { this.bookName = bookName; }

        public String getDueDate() { return dueDate; }
        public void setDueDate(String dueDate) { this.dueDate = dueDate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
