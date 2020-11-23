<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link href="css/home.css" rel="stylesheet">
    <%@ include file="parts/header.jsp" %>
</head>
<body>
<header>
    <nav id="header" class="p5 navbar navbar-expand-lg">
        <a class="navbar-brand" href="#">Notes Management</a>
        <!-- <a class="navbar-brand" href="#"><img src="https://e7.pngegg.com/pngimages/977/1014/png-clipart-sales-goal-buyer-management-chief-executive-sticky-notes-miscellaneous-text.png" alt="logo"></a>-->
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/home">Home <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/create">Create note</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/editProfile">Edit your profile</a>
                </li>
            </ul>
            <form method="POST" action="${pageContext.request.contextPath}/unlogin" class="inline">
                <input type="hidden" name="_csrftoken" value="${csrfToken}">
                <button type="submit" class="link-button">
                    Logout
                </button>
            </form>

            <span class="navbar-text"
                  style="padding-right: .5rem; padding-left: .5rem;">Welcome ${usernameSession.username}!</span>
        </div>
    </nav>
</header>
<div class="container">
    <div class="justify-content-center">
        <div class="">
            <form class="form-inline" action="/home" method="GET">
                <!-- <select name="typeNote" id="note"> -->
                <div class="form-group mb-3 mt-3 mr-1">
                    <select class="form-control" name="typeNote" id="selectTypeNote">
                        <c:choose>
                            <c:when test="${typeNote == 'compartides'}">
                                <option disabled="disabled">Select a type of note to filter</option>
                                <option value="propies">Created Notes</option>
                                <option selected="true" value="compartides">Shared Notes With You</option>
                                <option value="compartit">Shared Notes by you</option>
                            </c:when>
                            <c:when test="${typeNote == 'compartit'}">
                                <option disabled="disabled">Select a type of note to filter</option>
                                <option value="propies">Created Notes</option>
                                <option value="compartides">Shared Notes With You</option>
                                <option selected="true" value="compartit">Shared Notes by you</option>
                            </c:when>
                            <c:when test="${typeNote == 'propies'}">
                                <option disabled="disabled">Select a type of note to filter</option>
                                <option selected="true" value="propies">Created Notes</option>
                                <option value="compartides">Shared Notes With You</option>
                                <option value="compartit">Shared Notes by you</option>
                            </c:when>
                            <c:otherwise>
                                <option selected="true" disabled="disabled">Select a type of note to filter</option>
                                <option value="propies">Created Notes</option>
                                <option value="compartides">Shared Notes With You</option>
                                <option value="compartit">Shared Notes by you</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
                <div class="form-group mb-3 mt-3 mr-1 w-20">
                    <input type="text" class="form-control" placeholder="title" aria-label="Username"
                           aria-describedby="basic-addon1" name="titleFilter">
                </div>
                <div class="form-group mb-3 mt-3 mr-1 w-20">
                    <div class="">
                        <input class="form-control" type="datetime-local" id="startDate" name="noteStart"
                               value="${initDate}">
                    </div>
                </div>
                <div class="form-group mb-3 mt-3 mr-1 w-20">
                    <div class="">
                        <input class="form-control" type="datetime-local" id="endDate" name="noteEnd"
                               value="${endDate}">
                    </div>
                </div>
                <a class="btn btn-secondary" role="button" href="${pageContext.request.contextPath}/home">Restore</a>
                <button class="btn principalButton text-white" type="submit">Search</button>
            </form>
        </div>
    </div>
</div>
<section id="containerNotes">
    <div>
        <c:choose>
            <c:when test="${empty notes}">
                <h2>Nothing to display</h2>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${typeNote == 'compartides'}">
                        <h2>Your notes that have been shared with you</h2>
                    </c:when>
                    <c:when test="${typeNote == 'compartit'}">
                        <h2>Your notes that you have shared</h2>
                    </c:when>
                    <c:when test="${typeNote == 'propies'}">
                        <h2>Your notes that you have created</h2>
                    </c:when>
                    <c:otherwise>
                        <h2>All notes</h2>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
    <div id="siteNotes">
        <c:forEach var="note" items="${notes}">
            <c:choose>
                <c:when test="${note.owner.userid == useridSession}">
                    <c:choose>
                        <c:when test="${note.sharedUsers != null || typeNote == 'compartit'}">
                            <div class="card" style="width: 18rem; background-color: #53b3cb; color: black">
                                <div class="card-body">
                                    <h5 class="card-title">${note.title}</h5>
                                    <h6 class="card-subtitle mb-2 text-muted">Created By ${note.owner.username}</h6>
                                    <h6>
                                        Shared with
                                        <c:forEach var="userS" items="${note.sharedUsers}">
                                            ${userS.username},
                                        </c:forEach>
                                    </h6>
                                    <p class="card-text"
                                       style="display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical;overflow: hidden;">${note.body}</p>
                                    <div class="optionsButtons">
                                        <a href="/edit?id=${note.noteid}">
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil-fill"
                                                 fill="currentColor"
                                                 xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd"
                                                      d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>
                                            </svg>
                                        </a>
                                        <a data-toggle="modal" data-target="#modalDelete"
                                           onclick="passNoteId('/delete', ${note.noteid})">
                                            <svg width="1em" height="1em" viewBox="0 0 16 16"
                                                 class="bi bi-trash-fill"
                                                 fill="currentColor"
                                                 xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd"
                                                      d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
                                            </svg>
                                        </a>
                                        <a class="card-link" href="/share?id=${note.noteid}">
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-share-fill"
                                                 fill="currentColor"
                                                 xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd"
                                                      d="M11 2.5a2.5 2.5 0 1 1 .603 1.628l-6.718 3.12a2.499 2.499 0 0 1 0 1.504l6.718 3.12a2.5 2.5 0 1 1-.488.876l-6.718-3.12a2.5 2.5 0 1 1 0-3.256l6.718-3.12A2.5 2.5 0 0 1 11 2.5z"/>
                                            </svg>
                                        </a>
                                        <a href="/deleteShare?id=${note.noteid}">
                                            <svg width="1em" height="1em" viewBox="0 0 16 16"
                                                 class="bi bi-person-x-fill"
                                                 fill="currentColor"
                                                 xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd"
                                                      d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6.146-2.854a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
                                            </svg>
                                        </a>
                                    </div>

                                    <h6 class="card-subtitle mb-2 text-muted dateInfo"
                                        style="font-size: 10px; margin-top: 10px;">Last
                                        Modification ${note.lastModification}</h6>
                                </div>
                                <a href="/detail?id=${note.noteid}">
                                    <span class="link-spanner"></span>
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="card" style="width: 18rem; background-color: #f9c22e; color: black">
                                <div class="card-body">
                                    <h5 class="card-title">${note.title}</h5>
                                    <h6 class="card-subtitle mb-2 text-muted">Created By ${note.owner.username}</h6>
                                    <p class="card-text"
                                       style="display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical;overflow: hidden;">${note.body}</p>

                                    <div class="optionsButtons">
                                        <a href="/edit?id=${note.noteid}">
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil-fill"
                                                 fill="currentColor"
                                                 xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd"
                                                      d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>
                                            </svg>
                                        </a>
                                        <a data-toggle="modal" data-target="#modalDelete"
                                           onclick="passNoteId('/delete', ${note.noteid})">
                                            <svg width="1em" height="1em" viewBox="0 0 16 16"
                                                 class="bi bi-trash-fill"
                                                 fill="currentColor"
                                                 xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd"
                                                      d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5a.5.5 0 0 0-1 0v7a.5.5 0 0 0 1 0v-7z"/>
                                            </svg>
                                        </a>
                                        <a class="card-link" href="/share?id=${note.noteid}">
                                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-share-fill"
                                                 fill="currentColor"
                                                 xmlns="http://www.w3.org/2000/svg">
                                                <path fill-rule="evenodd"
                                                      d="M11 2.5a2.5 2.5 0 1 1 .603 1.628l-6.718 3.12a2.499 2.499 0 0 1 0 1.504l6.718 3.12a2.5 2.5 0 1 1-.488.876l-6.718-3.12a2.5 2.5 0 1 1 0-3.256l6.718-3.12A2.5 2.5 0 0 1 11 2.5z"/>
                                            </svg>
                                        </a>
                                    </div>

                                    <h6 class="card-subtitle mb-2 text-muted dateInfo"
                                        style="font-size: 10px; margin-top: 10px;">Last
                                        Modification ${note.lastModification}</h6>
                                </div>
                                <a href="/detail?id=${note.noteid}">
                                    <span class="link-spanner"></span>
                                </a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>


                    <div class="card" style="width: 18rem; background-color: #f15946; color: white">
                        <div class="card-body">
                            <h5 class="card-title">${note.title}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">Created By ${note.owner.username}</h6>
                            <p class="card-text"
                               style="display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical;overflow: hidden;">${note.body}</p>

                            <div class="optionsButtons">
                                <a data-toggle="modal" data-target="#modalDelete"
                                   onclick="passNoteId('/deleteAllShare', ${note.noteid})">
                                    <svg width="1em" height="1em" viewBox="0 0 16 16"
                                         class="bi bi-person-x-fill"
                                         fill="currentColor"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path fill-rule="evenodd"
                                              d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6.146-2.854a.5.5 0 0 1 .708 0L14 6.293l1.146-1.147a.5.5 0 0 1 .708.708L14.707 7l1.147 1.146a.5.5 0 0 1-.708.708L14 7.707l-1.146 1.147a.5.5 0 0 1-.708-.708L13.293 7l-1.147-1.146a.5.5 0 0 1 0-.708z"/>
                                    </svg>
                                </a>

                            </div>

                            <h6 class="card-subtitle mb-2 text-muted dateInfo"
                                style="font-size: 10px; margin-top: 10px;">Last
                                Modification ${note.lastModification}</h6>
                        </div>
                        <a href="/detail?id=${note.noteid}">
                            <span class="link-spanner"></span>
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
    <nav id="pagination" aria-label="Page navigation example">
        <ul class="pagination">
            <c:if test="${currentPage != 1}">
                <li class="page-item">
                    <a class="page-link principalButton"
                       href="/home?currentPage=${currentPage-1}&${filterURL}">Previous</a>
                </li>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <li class="page-item active">
                            <a class="page-link principalButton">
                                    ${i}
                                <span class="sr-only">(current)</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link principalButton" href="/home?currentPage=${i}&${filterURL}">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${currentPage lt totalPages}">
                <li class="page-item">
                    <a class="page-link principalButton" href="/home?currentPage=${currentPage+1}&${filterURL}">Next</a>
                </li>
            </c:if>
        </ul>
    </nav>
</section>

<!-- Modal to Delete note -->
<div class="modal fade" id="modalDelete" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleDeleteModal">Are you sure to delete this note?</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Go Back</button>
                <form id="formModal" class="card-link" method="POST" action="">
                    <input type="hidden" name="_csrftoken" value="${csrfToken}">
                    <input id="deleteNoteHidden" type="hidden" name="noteid" value="">
                    <button type="submit" class="btn btn-danger">Delete</button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="parts/footer.jsp" %>
</body>
</html>
