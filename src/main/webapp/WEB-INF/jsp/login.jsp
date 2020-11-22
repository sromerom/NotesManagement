<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<body>
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <div class="col-6">
            <c:if test="${noerror == false}">
                <div class="alert alert alert-danger alert-dismissible fade show" role="alert">
                    Invalid email address/password
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:if>
            <!-- Form -->
            <form method="POST" action="/login">
                <h1>Management Notes Login</h1>
                <!-- Input fields -->
                <div class="form-group mt-2">
                    <label for="username" class="col-sm-2 col-form-label">Username</label>
                    <input id="username" type="text" class="form-control rounded-right" name="username" required>
                </div>
                <div class="form-group mt-2">
                    <label for="password" class="col-sm-2 col-form-label">Password</label>
                    <input type="password" id="password" class="form-control"
                           aria-describedby="passwordHelpBlock" name="password" required>
                </div>
                <input type="hidden" name="_csrftoken" value="${csrfToken}">
                <button type="submit" class="btn btn-primary btn-customized">Login</button>
                <!-- End input fields -->
            </form>
            <!-- Form end -->
            <p><a href="/register">Create your account</a></p>
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
