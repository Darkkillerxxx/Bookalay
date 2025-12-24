package com.Bookalay.pojo;

import java.util.List;

public class AdminDashboardData {

    private int totalUsers;
    private int booksIssued;
    private int booksRead;
    private int usersWaitingForApproval;
    private int inactiveUsers;
    private int overdueBooks;
    private int totalBooksAvailable;
    private int newBooksRequestedToday;
    
    
    private List<Request> upcomingReturns;
    private List<Request> todayReturns;
    private List<Request> recentIssues;
    private List<Request> lateReturns;
    private List<ParentUser> usersForApprovals;
    private List<Request> booksOverdue;

    // ===== Getters & Setters =====

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public int getBooksIssued() {
        return booksIssued;
    }

    public void setBooksIssued(int booksIssued) {
        this.booksIssued = booksIssued;
    }

    public int getBooksRead() {
        return booksRead;
    }

    public void setBooksRead(int booksRead) {
        this.booksRead = booksRead;
    }

    public int getUsersWaitingForApproval() {
        return usersWaitingForApproval;
    }

    public void setUsersWaitingForApproval(int usersWaitingForApproval) {
        this.usersWaitingForApproval = usersWaitingForApproval;
    }

    public int getInactiveUsers() {
        return inactiveUsers;
    }

    public void setInactiveUsers(int inactiveUsers) {
        this.inactiveUsers = inactiveUsers;
    }

    public int getOverdueBooks() {
        return overdueBooks;
    }

    public void setOverdueBooks(int overdueBooks) {
        this.overdueBooks = overdueBooks;
    }

    public int getTotalBooksAvailable() {
        return totalBooksAvailable;
    }

    public void setTotalBooksAvailable(int totalBooksAvailable) {
        this.totalBooksAvailable = totalBooksAvailable;
    }

    public int getNewBooksRequestedToday() {
        return newBooksRequestedToday;
    }

    public void setNewBooksRequestedToday(int newBooksRequestedToday) {
        this.newBooksRequestedToday = newBooksRequestedToday;
    }

    public List<Request> getUpcomingReturns() {
        return upcomingReturns;
    }

    public void setUpcomingReturns(List<Request> upcomingReturns) {
        this.upcomingReturns = upcomingReturns;
    }
    
    public List<Request> getTodayReturns() {
        return todayReturns;
    }

    public void setTodayReturns(List<Request> todayReturns) {
        this.todayReturns = todayReturns;
    }
    
    public List<Request> getRecentIssues() {
        return recentIssues;
    }

    public void setRecentIssues(List<Request> recentIssues) {
        this.recentIssues = recentIssues;
    }
    
    public List<Request> getBooksOverdue() {
        return booksOverdue;
    }

    public void setBooksOverdue(List<Request> booksOverdue) {
        this.booksOverdue = booksOverdue;
    }
    
    public List<ParentUser> getUsersForApproval() {
        return usersForApprovals;
    }

    public void setUsersForApproval(List<ParentUser> usersForApprovals) {
        this.usersForApprovals = usersForApprovals;
    }

	public List<Request> getLateReturns() {
		return lateReturns;
	}

	public void setLateReturns(List<Request> lateReturns) {
		this.lateReturns = lateReturns;
	}

}
