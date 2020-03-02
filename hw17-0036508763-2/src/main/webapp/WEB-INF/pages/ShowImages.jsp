<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: marko
  Date: 11.07.19.
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Images</h1>

<c:forEach var="e" items="${imageNames}">
    <li><a href="image?imageName=${e}">${e}</a></li>
</c:forEach>




<%if (request.getAttribute("invalid") != null) {%>
Invalid data given!
<%}%>
<div>


    <form action="${pageContext.request.contextPath}/create" method="get">

        <div>
            <span class="formLabel">Title</span><input type="text" name="name" value='' size="20">
        </div>

    <div>
        <span class="formLabel">Data</span><textarea type="text" name="data" value='' size="20"></textarea>
    </div>

        <div class="formControls">
            <span class="formLabel">&nbsp;</span>
            <input type="submit" value="add">
        </div>

    </form>

</div>

</body>
</html>
