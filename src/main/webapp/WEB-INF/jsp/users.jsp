<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="POST" action="/share">
    <c:forEach var="user" items="${users}">
        <div>
            <input type="checkbox" id="${user.username}" name="share" value="${user.username}">
            <label for="${user.username}">${user.username}</label>
        </div>
    </c:forEach>
    <button type="submit">Envia</button>
</form>

</body>
</html>
