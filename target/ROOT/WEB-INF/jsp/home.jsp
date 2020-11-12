<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
          integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<body>
<h1>Gestio de notes</h1>
<a href="/unlogin">Cerrar session</a>

<a href="/create">Crea una nova nota</a>
<form action="/home" method="GET">
    <input type="text" name="titleFilter" placeholder="titol">
    <input type="date" id="start" name="noteStart">
    <input type="date" id="end" name="noteEnd">
    <button type="submit">Search</button>
</form>
<a href="/home">Restore</a>
<section id="siteNotes">
    <h2>Notes propies:</h2>
    <c:forEach var="note" items="${notes}">
        <div class="card" style="width: 18rem;">
            <div class="card-body">
                <h5 class="card-title">${note.title}</h5>
                <h6 class="card-subtitle mb-2 text-muted" style="font-size: 10px;">By ${note.user.username}</h6>
                <p class="card-text">${note.body}</p>
                <a class="card-link" href="/edit?id=${note.idnote}">Update</a>
                <a class="card-link" href="/delete?id=${note.idnote}">Delete</a>
                <a class="card-link" href="/users?id=${note.idnote}">Share</a>
            </div>
        </div>
    </c:forEach>
    <nav aria-label="Navigation for countries">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item"><a class="page-link" href="/home?currentPage=${currentPage-1}">Previous</a></li>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active"><a class="page-link">${i} <span
                                class="sr-only">(current)</span></a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="/home?currentPage=${i}">${i}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt totalPages}">
                <li class="page-item"><a class="page-link" href="/home?currentPage=${currentPage+1}">Next</a></li>
            </c:if>
        </ul>
    </nav>
</section>
<section id="siteSharedNotes">
    <div>
        <h2>Notes compartides amb tu:</h2>
        <c:forEach var="sharedNote" items="${sharedNotesWithMe}">
            <div style="background-color: cadetblue;">
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
            <div style="background-color: darkgoldenrod;">
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
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx"
        crossorigin="anonymous"></script>
</body>
</html>
