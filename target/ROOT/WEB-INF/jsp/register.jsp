<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
<h1>Registra el teu usuari per poder accedir a la aplicació desde aquest formulari!</h1>
<c:if test = "${noerror == false}">
    <div><p>No s'ha pogut crear l'usuari correctament...</p></div>
</c:if>

<form method="POST" action="/register">
    Email <input type="text" name="newEmail"><br>
    Nom usuari: <input type="text" name="newUser"><br>
    Password: <input type="password" name="newPass"><br>
    <button type="submit">Register</button>
</form>
</body>
</html>
