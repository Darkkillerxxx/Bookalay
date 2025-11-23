package com.Bookalay.pojo;

public class ChildUser {

    private int childId;
    private int parentId;
    private String childName;
    private Integer age;                 // age can be NULL → use Integer
    private String gender;
    private String interests;
    private String readingLevel;
    private String genres;
    private String readingFrequency;
    private String notes;

    // Default Constructor
    public ChildUser() {
    }

    // Parameterized Constructor
    public ChildUser(int childId, int parentId, String childName, Integer age, String gender,
                     String interests, String readingLevel, String genres,
                     String readingFrequency, String notes) {

        this.childId = childId;
        this.parentId = parentId;
        this.childName = childName;
        this.age = age;
        this.gender = gender;
        this.interests = interests;
        this.readingLevel = readingLevel;
        this.genres = genres;
        this.readingFrequency = readingFrequency;
        this.notes = notes;
    }

    // Getters & Setters

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getReadingLevel() {
        return readingLevel;
    }

    public void setReadingLevel(String readingLevel) {
        this.readingLevel = readingLevel;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getReadingFrequency() {
        return readingFrequency;
    }

    public void setReadingFrequency(String readingFrequency) {
        this.readingFrequency = readingFrequency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ChildUser [childId=" + childId + ", parentId=" + parentId +
                ", childName=" + childName + ", age=" + age + ", gender=" + gender +
                ", interests=" + interests + ", readingLevel=" + readingLevel +
                ", genres=" + genres + ", readingFrequency=" + readingFrequency +
                ", notes=" + notes + "]";
    }
}
