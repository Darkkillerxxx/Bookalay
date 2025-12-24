package com.Bookalay.dao;

import java.util.List;
import java.util.Map;

import com.Bookalay.pojo.ParentUser;
import com.Bookalay.pojo.Request;

public interface DashboardDao {

    // Aggregate metric methods
    int getTotalRequests(int parentId);

    int getTotalApproved(int parentId);

    int getBooksCurrentlyIssued(int parentId);

    int getBooksReturned(int parentId);

    int getPendingRequests(int parentId);

    int getOverdueBooksCount(int parentId);

    Map<String, Integer> getCountsByStatus(int parentId);

    // Table / list methods
    List<Request> getRecentRequests(int parentId, boolean applyLimit);

    List<Request> getIssuedBooks(int parentId,boolean applyLimit);

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

	List<ParentUser> getUsersForApprovalForAdmin();

	List<Request> getOverdueBooksAdmin();

	List<Request> getReturnedLateRequests(int parentId);
}
