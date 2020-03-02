<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
</head>
<body>
    <h1>Create new blog entry!</h1>
    <form action="${pageContext.request.contextPath}/servleti/author/${nick}/save/${blogEntry.id}" method="post">

        <div>
            <span class="formLabel">Title</span><input type="text" name="title" value='<c:out value="${blogEntry.title}"/>' size="20">
        </div>

        <div>
            <span class="formLabel">Text</span><textarea name="text" cols="40" rows="5" size="20"><c:out value="${blogEntry.text}"/></textarea>
        </div>

        <div class="formControls">
            <span class="formLabel">&nbsp;</span>
            <input type="submit" name="metoda" value="Create">
        </div>

    </form>
</body>
</html>
