<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" session="true" %>
<html>
<head>
    <title>Background color chooser</title>
</head>
<body style="background-color:<%=session.getAttribute("pickedBgCol")%>">

    <a href="setcolor?color=FFFFFF">WHITE</a>
    <a href="setcolor?color=FF0000">RED</a>
    <a href="setcolor?color=008000">GREEN</a>
    <a href="setcolor?color=00FFFF">CYAN</a>
</body>
</html>
