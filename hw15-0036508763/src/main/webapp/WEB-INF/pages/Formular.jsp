<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<title>Login</title>

		<style type="text/css">
		.greska {
		   font-family: fantasy;
		   font-weight: bold;
		   font-size: 0.9em;
		   color: #FF0000;
		   padding-left: 110px;
		}
		.formLabel {
		   display: inline-block;
		   width: 100px;
                   font-weight: bold;
		   text-align: right;
                   padding-right: 10px;
		}
		.formControls {
		  margin-top: 10px;
		}
		</style>
	</head>

	<body>
	<% if (session.getAttribute("current.user.nick") != null) { %>
		You are now logged in as: <%=session.getAttribute("current.user.nick")%>
		<a href="${pageContext.request.contextPath}/servleti/logout">logout</a>
		<br>
	<% } else { %>
		<h1>Login</h1>

		<form action="${pageContext.request.contextPath}/servleti/main/login" method="post">

		 <div>
			 <div>
		  <span class="formLabel">Nick</span><input type="text" name="nick" value='<c:out value="${user.nick}"/>' size="5">
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
			<c:if test="${user.hasError('login')}">
				<div class="greska"><c:out value="${user.getError('login')}"/></div>
			</c:if>
		<div class="formControls">
		  <span class="formLabel">&nbsp;</span>
		  <input type="submit" name="metoda" value="Login">
		</div>

		</form>

		<a href="${pageContext.request.contextPath}/servleti/register">Register here!</a>

	<% } %>
		<br>
		<h2>List of users:</h2>
		<c:forEach var="e" items="${users}">
			<li><a href="${pageContext.request.contextPath}/servleti/author/${e.nick}">${e.firstName} ${e.lastName}</a></li>
		</c:forEach>
	</body>

</html>
