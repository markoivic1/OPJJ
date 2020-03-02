<%--
  Created by IntelliJ IDEA.
  User: marko
  Date: 11.07.19.
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

Name: <%= request.getAttribute("imageName")%>

Number of Lines: <%= request.getAttribute("nLines")%>
Number of Circles: <%= request.getAttribute("nCircles")%>
Number of Filled Circles: <%= request.getAttribute("nFCircles")%>
Number of Filled triangles: <%= request.getAttribute("nTriangles")%>

<img alt="Image"src="/grafika/renderImage?imageName=<%=request.getAttribute("imageName")%>"width="400"height="400"/>

<form method="get" action="/grafika/main">
    <button type="submit">Home</button>
</form>

</body>
</html>
