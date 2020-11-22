<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Detail Note</title>
    <%@ include file="parts/header.jsp" %>
</head>
<body>

<main>
    <h1><c:out value="${titleNote}"/></h1>
    <section>
        ${bodyNote}
    </section>
</main>
<%@ include file="parts/footer.jsp" %>
</body>
</html>
