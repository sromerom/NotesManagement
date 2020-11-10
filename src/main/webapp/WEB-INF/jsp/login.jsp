<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>LOGIN:</h1>
<form method="POST" action="/login">
    Nom usuari: <input type="text" name="username"><br>
    Password: <input type="password" name="password"><br>
    <button type="submit">Login</button>
</form>
<a href="/register">No tens usuari? Crea'n un!</a>
</body>
</html>
