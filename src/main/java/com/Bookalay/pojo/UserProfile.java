package com.Bookalay.pojo;

public class UserProfile {

    private User user;          // full User object
    private ParentUser parent;  // full ParentUser object
    private ChildUser child;    // full ChildUser object

    // Getters and Setters
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public ParentUser getParent() {
        return parent;
    }
    public void setParent(ParentUser parent) {
        this.parent = parent;
    }

    public ChildUser getChild() {
        return child;
    }
    public void setChild(ChildUser child) {
        this.child = child;
    }
}
