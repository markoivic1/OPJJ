<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Register</title>
</head>
<body>
<body>
<h1>Login</h1>

<form action="${pageContext.request.contextPath}/servleti/register/save" method="post">

    <div>
        <div>
        <span class="formLabel">First Name</span><input type="text" name="firstname" value='<c:out value="${user.firstName}"/>' size="20">
        </div>
        <c:if test="${user.hasError('firstName')}">
            <div class="greska"><c:out value="${user.getError('firstName')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
        <span class="formLabel">Last Name</span><input type="text" name="lastname" value='<c:out value="${user.lastName}"/>' size="20">
        </div>
        <c:if test="${user.hasError('lastName')}">
            <div class="greska"><c:out value="${user.getError('lastName')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
        <span class="formLabel">EMail</span><input type="text" name="email" value='<c:out value="${user.email}"/>' size="20">
        </div>
        <c:if test="${user.hasError('email')}">
            <div class="greska"><c:out value="${user.getError('email')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
        <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${user.nick}"/>'size="5">
        </div>
        <c:if test="${user.hasError('nick')}">
            <div class="greska"><c:out value="${user.getError('nick')}"/></div>
        </c:if>
    </div>

    <div>
        <div>
        <span class="formLabel">Password</span><input type="password" name="password" value='' size="20">
        </div>
        <c:if test="${user.hasError('password')}">
            <div class="greska"><c:out value="${user.getError('password')}"/></div>
        </c:if>
    </div>

    <div class="formControls">
        <span class="formLabel">&nbsp;</span>
        <input type="submit" name="metoda" value="Register">
    </div>

</form>
</body>
</html>
