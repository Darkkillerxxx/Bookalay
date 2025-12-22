<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>
    <c:choose>
        <c:when test="${not empty book}">${book.title}</c:when>
        <c:otherwise>Create / Edit Book</c:otherwise>
    </c:choose>
</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
      rel="stylesheet">
      

<style>
/* 🔒 UNCHANGED */
body {
    background: #f8f9fc;
    font-family: Arial, sans-serif;
}

.book-container {
    display: flex;
    gap: 40px;
    padding: 40px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.book-img img {
    width: 280px;
    height: 380px;
    object-fit: cover;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
}

.book-title {
    font-size: 26px;
    font-weight: 700;
    margin-bottom: 5px;
}

.info-row strong {
    width: 150px;
    display: inline-block;
}
</style>
</head>

<body>



<div class="container-fluid p-4">

    <c:if test="${!isAdmin}">
        <%@ include file="parentDashboardCommon.jsp"%>
    </c:if>

    <c:if test="${isAdmin}">
        <%@ include file="adminDashboardCommon.jsp"%>
    </c:if>

    <div class="book-container mt-3">

        <!-- LEFT SECTION -->
        <div class="book-img">
            <img
                src="${not empty book && not empty book.coverArt
                        ? book.coverArt
                        : 'https://via.placeholder.com/300x400'}"
                alt="Book Image">
        </div>

        <!-- RIGHT SECTION -->
        <div class="book-details">

            <div class="book-title">
                <c:choose>
                    <c:when test="${not empty book}">${book.title}</c:when>
                    <c:otherwise>New Book</c:otherwise>
                </c:choose>
            </div>

            <div class="mt-1">
                <span>${not empty book ? book.author : ''}</span>
            </div>

            <p class="mt-3 text-muted" style="max-width: 95%;">
                ${not empty book ? book.summary : ''}
            </p>

            <div class="mt-3">
                <div class="info-row mb-1">
                    <strong>Added Date:</strong>
                    ${not empty book ? book.dateAdded : ''}
                </div>

                <div class="info-row mb-1">
                    <strong>Pages:</strong>
                    ${not empty book ? book.pageCount : ''}
                </div>

                <div class="info-row mb-1">
                    <strong>Genre:</strong>
                    ${not empty book ? book.genre : ''}
                </div>

                <div class="info-row mb-1">
                    <strong>Interest:</strong>
                    ${not empty book ? book.interest : ''}
                </div>

                <div class="info-row mb-1">
                    <strong>Reading Level:</strong>
                    ${not empty book ? book.readingDifficulty : ''}
                </div>

                <c:if test="${isAdmin}">
                    <div class="info-row mb-1">
                        <strong>Available Copies:</strong>
                        ${not empty book ? book.availableCopies : ''}
                    </div>
                </c:if>
            </div>

            <!-- ACTIONS -->
            <c:if test="${!isAdmin && not empty book}">
                <div class="mt-4">
                    <a href="RequestController?action=showRequestForm&bookId=${book.bookId}"
                       class="btn btn-primary">
                        Request this book
                    </a>
                </div>
            </c:if>

            <c:if test="${isAdmin && not empty book}">
                <div class="d-flex mt-3">
                    <a href="BooksController?action=createEditBooks&bookId=${book.bookId}"
                       class="btn btn-warning me-2">
                        Edit Details
                    </a>

                    <c:if test="${book.available}">
                        <a href="BooksController?action=toggleAvailability&bookId=${book.bookId}"
                           class="btn btn-danger">
                            Inactivate this book
                        </a>
                    </c:if>

                    <c:if test="${!book.available}">
                        <a href="BooksController?action=toggleAvailability&bookId=${book.bookId}"
                           class="btn btn-success">
                            Activate this Book
                        </a>
                    </c:if>
                </div>
            </c:if>

        </div>
    </div>
</div>

</body>
</html>
