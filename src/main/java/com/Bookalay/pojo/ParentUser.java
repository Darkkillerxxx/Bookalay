package com.Bookalay.pojo;

import java.time.LocalDateTime;
import java.util.List;

public class ParentUser {

    private int parentId;
    private int userId;
    private String parentName;
    private String email;
    private String phone;
    private LocalDateTime registrationDate;
    private Boolean isActive;
    private List<ChildUser> childUsers;

    // ===== Getters and Setters =====
    
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getParentName() {
        return parentName;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getEmail() {
        return email;
    }
    
    public List<ChildUser> getChildUsers() {
        return childUsers;
    }

    public void setChildUsers(List<ChildUser> childUsers) {
        this.childUsers = childUsers;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		
	}
}
