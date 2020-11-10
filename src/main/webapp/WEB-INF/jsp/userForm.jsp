<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form method="POST" action="${action}">
    Title: <input type="text" name="title"><br>
    body: <textarea id="bodyContent" name="bodyContent"></textarea>
    <button type="submit">Create</button>
</form>
</body>
</html>
