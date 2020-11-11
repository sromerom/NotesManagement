<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${noerror == false && action == '/create'}">
    <div><p>No s'ha pogut crear la nota correctament...</p></div>
</c:if>
<c:if test="${noerror == false && action == '/edit'}">
    <div><p>No s'ha pogut editar la nota correctament...</p></div>
</c:if>
<form method="POST" action="${action}">
    <c:if test="${action == '/create'}">
        Title: <input type="text" name="title"><br>
        body: <textarea id="bodyContent" name="bodyContent"></textarea>
    </c:if>

    <c:if test="${action == '/edit'}">
        Title: <input type="text" name="title" value="${title}"><br>
        body: <textarea id="bodyContent" name="bodyContent">${body}</textarea>
    </c:if>
    <button type="submit">Envia</button>
</form>

</body>
</html>
