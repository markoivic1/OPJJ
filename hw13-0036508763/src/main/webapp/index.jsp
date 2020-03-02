<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
</head>
<body style="background-color:<%=session.getAttribute("pickedBgCol")%>">
    <a href="colors.jsp">Background color chooser</a>
    <a href="trigonometric?a=0&b=90">Sin and Cos</a>
    <a href="stories/funny.jsp">Funny story</a>
    <a href="powers?a=1&b=100&n=3">Powers</a>
    <a href="appinfo.jsp">App info</a>
    <form action="trigonometric" method="GET">
        Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
        Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
        <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
    </form>
</body>
</html>
