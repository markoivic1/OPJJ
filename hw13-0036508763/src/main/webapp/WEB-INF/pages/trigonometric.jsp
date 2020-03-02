<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Trigonometric</title>
</head>
<body style="background-color:<%=session.getAttribute("pickedBgCol")%>">
<ol style="background-color:<%=session.getAttribute("pickedBgCol")%>">
    <table border="1">
        <tr><td>Value</td><td>Sin</td><td>Cos</td></tr>
        <c:forEach var = "i" begin = "${a}" end = "${b}">
            <tr><td>${i}</td><td>${sin[i - a]}</td><td>${cos[i - a]}</td></tr>
        </c:forEach>
    </table>
</ol>
</body>
</html>
