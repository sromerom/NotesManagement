<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/css/select2.min.css" rel="stylesheet"/>
    <style>

        #container {
            width: 100vw;
            height: 100vh;
            display: flex;
            flex-wrap: wrap;
            flex-direction: row;
            justify-content: space-between;
            align-items: center;
        }

        #container div {
            text-align: center;
            flex-basis: 50%;
            height: 160px;
        }

        form {
            display: flex;
            flex-direction: column;
            flex-wrap: nowrap;
            justify-content: center;
            align-items: center;
            margin: 15px;
        }

        select {
            width: 100%;
        }

        button {
            margin: 15px 15px;
        }

        p {
            text-align: center;
        }

        /*
        section {
            display: flex;
            width: 100vw;
            height: 100vh;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }


        */
    </style>
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
<script
        src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/js/select2.min.js"></script>
<script>
    $(document).ready(function () {
        $('.js-example-basic-multiple').select2();
    });
</script>
</body>
</html>
