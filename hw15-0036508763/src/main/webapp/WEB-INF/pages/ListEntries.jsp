<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
</head>
<body>

<c:forEach var="e" items="${entries}">
    <li>${e.title} <a href="${pageContext.request.contextPath}/servleti/author/${nick}/edit/${e.id}">(edit)</a></li>
</c:forEach>
</body>
</html>
