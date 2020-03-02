<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body>

  <c:choose>
    <c:when test="${blogEntry==null}">
      Nema unosa!
    </c:when>
    <c:otherwise>

      <% if (session.getAttribute("current.user.nick") != null) {
        if (session.getAttribute("current.user.nick").equals(request.getAttribute("nick"))) { %>
      <a href="${pageContext.request.contextPath}/servleti/author/${nick}/edit/${blogEntry.id}">Edit</a>
      <% }} %>

      <h1><c:out value="${blogEntry.title}"/></h1>
      <p><c:out value="${blogEntry.text}"/></p>
      <c:if test="${!blogEntry.comments.isEmpty()}">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li><div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out value="${e.postedOn}"/></div><div style="padding-left: 10px;"><c:out value="${e.message}"/></div></li>
      </c:forEach>
      </ul>
      </c:if>

  <form action="${pageContext.request.contextPath}/servleti/comment" method="post">
    <input type="hidden" name="entryID" value="${blogEntry.id}" />

    <% if (session.getAttribute("current.user.nick") == null) { %>
    <div>
      <span class="formLabel">Email</span><input type="text" name="email" value='<c:out value="${email}"/>' size="5">
    </div>
    <% } %>
    <div>
      <span class="formLabel">Message</span><textarea name="message" cols="40" rows="5" value='<c:out value="${message}"/>' size="20"></textarea>
    </div>

    <div class="formControls">
      <span class="formLabel">&nbsp;</span>
      <input type="submit" name="metoda" value="Add new comment">
    </div>

    </c:otherwise>
  </c:choose>

  </body>
</html>
