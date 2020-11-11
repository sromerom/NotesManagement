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
<a href="/"></a>
<section id="siteNotes">
    <h2>Notes propies:</h2>
    <c:forEach var="note" items="${notes}">
        <div style="background-color: burlywood; width: 100px; height: 200px">
            <h3>${note.title}</h3>
            <p>${note.body}</p>
            <a href="/edit?id=${note.idnote}">Update</a>
            <a href="/delete?id=${note.idnote}">Delete</a>
            <a href="/users?id=${note.idnote}">Share With Users!</a>
        </div>
    </c:forEach>
</section>
<section id="siteSharedNotes">
    <div>
        <h2>Notes compartides amb tu:</h2>
        <c:forEach var="sharedNote" items="${sharedNotesWithMe}">
            <div style="background-color: cadetblue; width: 100px; height: 200px">
                <h3>${sharedNote.note.title}</h3>
                <p>${sharedNote.note.body}</p>
                <span>Nota compartida per ${sharedNote.note.user.username}</span>
                <a href="/deleteShare?idShareNote=${sharedNote.idShareNote}">Delete share</a>
            </div>
        </c:forEach>
    </div>
    <div>
        <h2>Notes que he compartit:</h2>
        <c:forEach var="sharedNote" items="${sharedNotes}">
            <div style="background-color: darkgoldenrod; width: 100px; height: 200px">
                <h3>${sharedNote.note.title}</h3>
                <p>${sharedNote.note.body}</p>
                <a href="/edit?id=${sharedNote.note.idnote}">Update</a>
                <a href="/delete?id=${sharedNote.note.idnote}">Delete</a>
                <a href="/users?id=${sharedNote.note.idnote}">Share With Users!</a>
                <a href="/deleteShare?idShareNote=${sharedNote.idShareNote}">Delete share</a>
            </div>
        </c:forEach>
    </div>
</section>
</body>
</html>
