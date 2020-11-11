<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Si estas aqui, estas autenticado...</h1>
<a href="/unlogin">Cerrar session</a>

<a href="/create">Crea una nova nota</a>
<section id="siteNotes">
    <c:forEach var="note" items="${notes}">
        <div>
            <h3>${note.title}</h3>
            <p>${note.body}</p>
            <a href="/edit?id=${note.idnote}">Update</a>
            <a href="/delete?id=${note.idnote}">Delete</a>
            <a href="/users?id=${note.idnote}">Share With Users!</a>
        </div>
    </c:forEach>
</section>
</body>
</html>
