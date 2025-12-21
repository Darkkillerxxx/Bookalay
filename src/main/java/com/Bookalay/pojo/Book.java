package com.Bookalay.pojo;

public class Book {

    private int bookId;
    private String title;
    private String author;
    private String interest;
    private String genre;
    private String readingDifficulty;
    private String seriesName;
    private int pageCount;
    private String summary;
    private String coverArt;
    private boolean isAvailable;
    private int totalCopies;
    private int availableCopies;
    private String dateAdded;

    // ----- Constructors -----
    public Book() {}

    public Book(int bookId, String title, String author, String interest, String genre,
                String readingDifficulty, String seriesName, int pageCount, String summary,
                String coverArt, boolean isAvailable, int totalCopies, int availableCopies,
                String dateAdded) {

        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.interest = interest;
        this.genre = genre;
        this.readingDifficulty = readingDifficulty;
        this.seriesName = seriesName;
        this.pageCount = pageCount;
        this.summary = summary;
        this.coverArt = coverArt;
        this.isAvailable = isAvailable;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.dateAdded = dateAdded;
    }

    // ----- Getters & Setters -----

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReadingDifficulty() {
        return readingDifficulty;
    }

    public void setReadingDifficulty(String readingDifficulty) {
        this.readingDifficulty = readingDifficulty;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverArt() {
        return coverArt;
    }

    public void setCoverArt(String coverArt) {
        this.coverArt = coverArt;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}

