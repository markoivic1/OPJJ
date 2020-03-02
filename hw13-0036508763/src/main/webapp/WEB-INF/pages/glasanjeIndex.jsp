<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body style="background-color:<%=session.getAttribute("pickedBgCol")%>">
<h1>Glasanje za omiljeni bend:</h1>
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
<ol>
    <c:forEach var="e" items="${availableBands}">
        <li><a href="glasanje-glasaj?id=${e.ID}">${e.name}</a></li>
    </c:forEach>
</ol>
</body>
</html>
