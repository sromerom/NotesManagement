<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${action == '/share'}">
        <title>Share with users</title>
    </c:if>
    <c:if test="${action == '/deleteShare'}">
        <title>Delete shares</title>
    </c:if>
    <link href="css/users.css" rel="stylesheet">
    <%@ include file="parts/header.jsp" %>
</head>
<body>

<c:if test="${not empty noerror && action == '/share'}">
    <c:choose>
        <c:when test="${noerror == false && action == '/share'}">
            <div class="alert alert alert-danger alert-dismissible fade show" role="alert">
                The note could not be shared successfully
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                The note was shared successfully
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
<c:if test="${not empty noerror && action == '/deleteShare'}">
    <c:choose>
        <c:when test="${noerror == false && action == '/deleteShare'}">
            <div class="alert alert alert-danger alert-dismissible fade show" role="alert">
                Share could not be deleted
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                Share was successfully removed
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </c:otherwise>
    </c:choose>
</c:if>
<section id="container">
    <div>
        <h2>Users that you have shared for this note</h2>
        <ul class="list-group">
            <c:forEach var="user" items="${usersShared}">
                <li class="list-group-item">${user.username}</li>
            </c:forEach>
        </ul>
        <c:if test="${action == '/deleteShare'}">
            <form action="/deleteAllShare" method="POST">
                <input type="hidden" name="noteid" value="${noteid}">
                <input type="hidden" name="_csrftoken" value="${csrfToken}">
                <button type="submit" class="btn btn-danger">Delete all shares</button>
            </form>
        </c:if>
    </div>
    <div>
        <c:choose>
            <c:when test="${action == '/deleteShare'}">
                <h2>Delete the users you have shared</h2>
            </c:when>
            <c:otherwise>
                <h2>Share this note with some users</h2>
            </c:otherwise>
        </c:choose>
        <form method="POST" action="${action}">
            <input type="hidden" id="noteid" name="noteid" value="${noteid}">
            <select class="js-example-basic-multiple" name="states[]" multiple="multiple">
                <c:forEach var="user" items="${users}">
                    <option value="${user.username}">${user.username}</option>
                </c:forEach>
            </select>
            <small id="shareHelpBlock" class="form-text text-muted">
                Remember that you cannot delete or create the share of a user that already exists.
            </small>
            <input type="hidden" name="_csrftoken" value="${csrfToken}">
            <c:choose>
                <c:when test="${action == '/deleteShare'}">
                    <button type="submit" class="btn btn-danger">Delete specific share</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-success">Add shares</button>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</section>
<p><a href="${pageContext.request.contextPath}/home">Go to home</a></p>
<%@ include file="parts/footer.jsp" %>
<script>
    $(document).ready(function () {
        $('.js-example-basic-multiple').select2();
    });
</script>
</body>
</html>
