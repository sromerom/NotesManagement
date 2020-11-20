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
        section {
            display: flex;
            width: 100vw;
            height: 100vh;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        form {
            width: 30%;
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
    </style>
</head>
<body>
<section>
    <h1>Share With Users</h1>
    <form method="POST" action="/share">
        <input type="hidden" id="noteid" name="noteid" value="${noteid}">
        <select class="js-example-basic-multiple" name="states[]" multiple="multiple">
            <c:forEach var="user" items="${users}">
                <option value="${user.username}">${user.username}</option>
            </c:forEach>
        </select>
        <button type="submit" class="btn btn-success">Share</button>
    </form>
    <p><a href="${pageContext.request.contextPath}/home">Go to home</a></p>
</section>

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
