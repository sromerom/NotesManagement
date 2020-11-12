<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/css/select2.min.css" rel="stylesheet"/>
</head>
<body>
<!--
<form method="POST" action="/share">
    <c:forEach var="user" items="${users}">
        <div>
            <input type="hidden" id="noteid" name="noteid" value="${noteid}">
            <input type="checkbox" id="${user.username}" name="share" value="${user.username}">
            <label for="${user.username}">${user.username}</label>
        </div>
    </c:forEach>
    <button type="submit">Envia</button>
</form>
-->
<h1>Comparteix amb algun usuaris.</h1>
<form method="POST" action="/share">
    <input type="hidden" id="noteid" name="noteid" value="${noteid}">
    <select class="js-example-basic-multiple" name="states[]" multiple="multiple">
        <c:forEach var="user" items="${users}">
            <option value="${user.username}">${user.username}</option>
        </c:forEach>
    </select>
    <button type="submit">Envia</button>
</form>
<script
        src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-beta.1/dist/js/select2.min.js"></script>
<script>
    $(document).ready(function () {
        $('.js-example-basic-multiple').select2();
    });
</script>
</body>
</html>
