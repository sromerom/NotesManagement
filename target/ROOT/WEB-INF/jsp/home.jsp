<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Gestio de notes</h1>
<a href="/unlogin">Cerrar session</a>

<a href="/create">Crea una nova nota</a>
<section id="siteNotes">
    <h2>Notes propies:</h2>
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
<section id="siteSharedNotes">
    <h2>Notes compartides amb tu:</h2>
    <c:forEach var="sharedNote" items="${sharedNotes}">
        <div>
            <h3>${sharedNote.title}</h3>
            <p>${sharedNote.body}</p>
            <span>Nota compartida per ${sharedNote.user.username}</span>
        </div>
    </c:forEach>
</section>
</body>
</html>
