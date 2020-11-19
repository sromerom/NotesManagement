<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${action == '/create'}">
        <title>Add Note</title>
    </c:if>
    <c:if test="${action == '/edit'}">
        <title>Edit Note</title>
    </c:if>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<body>
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <div class="col-6">
            <c:if test="${noerror == false && action == '/create'}">
                <div class="alert alert alert-danger alert-dismissible fade show" role="alert">

                    The note could not be created successfully
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>

            <c:if test="${noerror == false && action == '/edit'}">
                <div class="alert alert alert-danger alert-dismissible fade show" role="alert">
                    The note could not be edited successfully
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <form class="form-example" method="POST" action="${action}">
                <c:if test="${action == '/create'}">
                    <h1>Create a new Note</h1>
                    <div class="form-group mt-2">
                        <label for="title" class="col-sm-2 col-form-label">Title</label>
                        <input id="title" type="text" class="form-control rounded-right" name="title" required>
                    </div>
                    <div class="form-group mt-2">
                        <label for="bodyContent">Body</label>
                        <textarea class="form-control" id="bodyContent" rows="3" name="bodyContent">${body}</textarea>
                    </div>
                    <button type="submit" class="btn btn-success">Add</button>
                </c:if>
                <c:if test="${action == '/edit'}">
                    <h1>Update a current note</h1>
                    <div class="form-group mt-2">
                        <label for="titleUpdate" class="col-sm-2 col-form-label">Title</label>
                        <input id="titleUpdate" type="text" class="form-control rounded-right" name="title"
                               value="${title}"
                               required>
                    </div>
                    <div class="form-group mt-2">
                        <label for="bodyContentUpdate">Body</label>
                        <textarea class="form-control" id="bodyContentUpdate" rows="3"
                                  name="bodyContent">${body}</textarea>
                    </div>
                    <button type="submit" class="btn btn-warning">Edit</button>
                </c:if>
            </form>
            <p><a href="${pageContext.request.contextPath}/home">Go to home</a></p>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>
</body>
</html>
