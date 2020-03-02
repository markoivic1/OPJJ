<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body style="background-color:<%=session.getAttribute("pickedBgCol")%>">
<h1>${requestScope.poll.title}</h1>
<p>${requestScope.poll.message}</p>
<ol>
    <c:forEach var="e" items="${availableBands}">
        <li><a href="${pageContext.request.contextPath}/servleti/glasanje-glasaj?id=${e.id}">${e.optionTitle}</a></li>
    </c:forEach>
</ol>
</body>
</html>
