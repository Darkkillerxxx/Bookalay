package com.Bookalay.service;

import java.util.List;
import java.util.Map;

import com.Bookalay.pojo.Request;
import com.Bookalay.pojo.User;

public interface DashboardService {
	 // Aggregate metric methods
    int getTotalRequests(int parentId);

    int getTotalApproved(int parentId);

    int getBooksCurrentlyIssued(int parentId);

    int getBooksReturned(int parentId);

    int getPendingRequests(int parentId);

    int getOverdueBooksCount(int parentId);

    Map<String, Integer> getCountsByStatus(int parentId);

    // Table / list methods
    List<Request> getRecentRequests(int parentId, int limit);

    List<Request> getIssuedBooks(int parentId);

    List<Request> getOverdueBooks(int parentId);

    List<Request> getReturnedBooks(int parentId);

    List<Request> getUpcomingDue(int parentId, int days);
    
    int getTotalUsersForAdmin();

	int getTotalBooksIssuedForAdmin();

	int getTotalBooksReadForAdmin();

	int getUsersWaitingForApproval();

	int getInactiveUsersForAdmin();

	int getOverdueBooksForAdmin();

	int getTotalBooksAvailableForAdmin();

	int getNewBooksRequestedToday();
	
	List<Request> getUpcomingDueRequestForAdmin(int days);

	List<Request> getTodayReturnRequestForAdmin();

	List<Request> getRecentBookIssuesForAdmin();

	List<User> getUsersForApprovalForAdmin();
	
	List<Request> getOverdueBooksAdmin();

}
