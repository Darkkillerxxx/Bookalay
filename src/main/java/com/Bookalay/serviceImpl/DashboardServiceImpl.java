package com.Bookalay.serviceImpl;

import java.util.List;
import java.util.Map;

import com.Bookalay.dao.DashboardDao;
import com.Bookalay.daoImpl.DashboardDaoImpl;
import com.Bookalay.pojo.ParentUser;
import com.Bookalay.pojo.Request;
import com.Bookalay.service.DashboardService;

public class DashboardServiceImpl implements DashboardService {

    // Prefer constructor or DI in real apps; simple direct instantiation here
    private final DashboardDao dashboardDao = new DashboardDaoImpl();

    // Aggregate metric methods

    @Override
    public int getTotalRequests(int parentId) {
        return dashboardDao.getTotalRequests(parentId);
    }

    @Override
    public int getTotalApproved(int parentId) {
        return dashboardDao.getTotalApproved(parentId);
    }

    @Override
    public int getBooksCurrentlyIssued(int parentId) {
        return dashboardDao.getBooksCurrentlyIssued(parentId);
    }

    @Override
    public int getBooksReturned(int parentId) {
        return dashboardDao.getBooksReturned(parentId);
    }

    @Override
    public int getPendingRequests(int parentId) {
        return dashboardDao.getPendingRequests(parentId);
    }

    @Override
    public int getOverdueBooksCount(int parentId) {
        return dashboardDao.getOverdueBooksCount(parentId);
    }

    @Override
    public Map<String, Integer> getCountsByStatus(int parentId) {
        return dashboardDao.getCountsByStatus(parentId);
    }

    // Table / list methods

    @Override
    public List<Request> getRecentRequests(int parentId, boolean applyLimit) {
        return dashboardDao.getRecentRequests(parentId, applyLimit);
    }

    @Override
    public List<Request> getIssuedBooks(int parentId,boolean applyLimit) {
        return dashboardDao.getIssuedBooks(parentId,applyLimit);
    }

    @Override
    public List<Request> getOverdueBooks(int parentId) {
        return dashboardDao.getOverdueBooks(parentId);
    }

    @Override
    public List<Request> getReturnedBooks(int parentId) {
        return dashboardDao.getReturnedBooks(parentId);
    }

    @Override
    public List<Request> getUpcomingDue(int parentId, int days) {
        return dashboardDao.getUpcomingDue(parentId, days);
    }

	@Override
	public int getTotalUsersForAdmin() {
		return dashboardDao.getTotalUsersForAdmin();
	}

	@Override
	public int getTotalBooksIssuedForAdmin() {
		return dashboardDao.getTotalBooksIssuedForAdmin();
	}

	@Override
	public int getTotalBooksReadForAdmin() {
		return dashboardDao.getTotalBooksReadForAdmin();
	}

	@Override
	public int getUsersWaitingForApproval() {
		return dashboardDao.getUsersWaitingForApproval();
	}

	@Override
	public int getInactiveUsersForAdmin() {
		return dashboardDao.getInactiveUsersForAdmin();
	}

	@Override
	public int getOverdueBooksForAdmin() {
		return dashboardDao.getOverdueBooksForAdmin();
	}

	@Override
	public int getTotalBooksAvailableForAdmin() {
		return dashboardDao.getTotalBooksAvailableForAdmin();
	}

	@Override
	public int getNewBooksRequestedToday() {
		return dashboardDao.getNewBooksRequestedToday();
	}

	@Override
	public List<Request> getUpcomingDueRequestForAdmin(int days) {
		return dashboardDao.getUpcomingDueRequestForAdmin(days);
	}

	@Override
	public List<Request> getTodayReturnRequestForAdmin() {
		return dashboardDao.getTodayReturnRequestForAdmin();
	}

	@Override
	public List<Request> getRecentBookIssuesForAdmin() {
		return dashboardDao.getRecentBookIssuesForAdmin();
	}

	@Override
	public List<ParentUser> getUsersForApprovalForAdmin() {
		return dashboardDao.getUsersForApprovalForAdmin();
	}

	@Override
	public List<Request> getOverdueBooksAdmin() {
		return dashboardDao.getOverdueBooksAdmin();
	}

	@Override
	public List<Request> getReturnedLateRequests(int parentId) {
		// TODO Auto-generated method stub
		return dashboardDao.getReturnedLateRequests(parentId);
	}
}
