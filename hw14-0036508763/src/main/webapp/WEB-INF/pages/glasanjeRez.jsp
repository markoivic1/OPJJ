<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style type="text/css">table.rez td {text-align: center;}</style>
</head>
<body style="background-color:<%=session.getAttribute("pickedBgCol")%>">
<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja.</p>
<table border="1" cellspacing="0" class="rez">
    <thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
    <c:forEach var="e" items="${rezultati}">
        <tr><td>${e.optionTitle}</td><td>${e.votesCount}</td></tr>
    </c:forEach>
    </tbody>
</table>
<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-chart"src="${pageContext.request.contextPath}/servleti/glasanje-grafika"width="400"height="400"/>
<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su
    <a href="${pageContext.request.contextPath}/servleti/glasanje-xls">ovdje</a></p>
<h2>Razno</h2>
<p>Primjeri pjesama pobjedničkih bendova:</p>
<ul>
    <c:forEach var="e" items="${pobjednici}">
        <li><a href=${e.optionLink}target="_blank">${e.optionTitle}</a></li>
    </c:forEach>
</ul>
</body>
</html>