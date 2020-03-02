<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Entries</title>
</head>
<body>
<h1>Entries for nick ${nick}</h1>
<c:forEach var="e" items="${entries}">
    <li><a href="${pageContext.request.contextPath}/servleti/author/${nick}/${e.id}">${e.title}</a></li>
</c:forEach>
<% if (session.getAttribute("current.user.nick") != null) {
    if (session.getAttribute("current.user.nick").equals(request.getAttribute("nick"))) { %>
    <a href="${pageContext.request.contextPath}/servleti/author/${nick}/new">New</a>
    <br>
    <a href="${pageContext.request.contextPath}/servleti/author/${nick}/edit">Edit</a>
<% }} %>
</body>
</html>
