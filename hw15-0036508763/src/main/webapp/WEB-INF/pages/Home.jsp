<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>

Your nick: <%=session.getAttribute("current.user.nick")%>
<br>
You are now logged in as: <%=session.getAttribute("current.user.fn")%> <%=session.getAttribute("current.user.ln")%>
<a href="${pageContext.request.contextPath}/servleti/logout">logout</a>
<br>

<br>
<h2>List of users:</h2>
<c:forEach var="e" items="${users}">
    <li><a href="${pageContext.request.contextPath}/servleti/author/${e.nick}">${e.firstName} ${e.lastName}</a></li>
</c:forEach>

</body>
</html>
