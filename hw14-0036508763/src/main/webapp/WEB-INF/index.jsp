<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Available Polls</title>
</head>
<body>
<h1>Available Polls:</h1>
<ol>
    <c:forEach var="e" items="${polls}">
        <li><a href="${pageContext.request.contextPath}/servleti/glasanje?pollID=${e.id}">${e.title}</a></li>
    </c:forEach>
</ol>
</body>
</html>
