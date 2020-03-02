<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.concurrent.TimeUnit" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %><html>
<head>

</head>
<body style="background-color:<%=session.getAttribute("pickedBgCol")%>">
    Server has been running for <%
        Long startupTime = (Long) request.getServletContext().getAttribute("time");
        Long currentTime = System.currentTimeMillis();

        String time = "";
        Long deltaTime = currentTime - startupTime;
        // days
        time += TimeUnit.MILLISECONDS.toDays(deltaTime) + " days ";
        if (TimeUnit.MILLISECONDS.toDays(deltaTime) != 0) {
            deltaTime %= TimeUnit.MILLISECONDS.toDays(deltaTime) * (1000*60*60*24);
        }

        // hours
        time += TimeUnit.MILLISECONDS.toHours(deltaTime) + " hours ";
        if (TimeUnit.MILLISECONDS.toHours(deltaTime) != 0) {
            deltaTime %= TimeUnit.MILLISECONDS.toHours(deltaTime) * (1000 * 60 * 60);
        }
        // minutes
        time += TimeUnit.MILLISECONDS.toMinutes(deltaTime) + " minutes ";
        if (TimeUnit.MILLISECONDS.toMinutes(deltaTime) != 0) {
            deltaTime %= TimeUnit.MILLISECONDS.toMinutes(deltaTime) * (1000 * 60);
        }
        // seconds
        time += TimeUnit.MILLISECONDS.toSeconds(deltaTime) + " seconds ";
        if (TimeUnit.MILLISECONDS.toSeconds(deltaTime) != 0) {
            deltaTime %= TimeUnit.MILLISECONDS.toSeconds(deltaTime) * (1000);
        }
        // millis
        time += " and " + deltaTime + " milliseconds ";

        out.write(time);%>

</body>
</html>
