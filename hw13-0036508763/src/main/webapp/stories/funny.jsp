<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Joke</title>
</head>
<body text = <%Random random = new Random();
        String textColor = "";
        for (int i = 0; i < 6; i++) {
        textColor += Integer.toString(random.nextInt(16), 16);
        }
        out.write(textColor);%> style="background-color:<%=session.getAttribute("pickedBgCol")%>">
    došo zec u pekaru i pita
    <br>
    -jel imate bureka od mrkve?
    <br>
    kaže pekar -nemamo
    <br>
    došo zec drugi dan i pita
    <br>
    -imate bureka od mrkve?
    <br>
    kaže pekar - nemamo
    <br>
    dođe zec treći dan i pita
    <br>
    -imate bureka od mrkve?
    <br>
    kaže pekar-nemamo
    <br>
    pekaru već dozlogrdi i on
    <br>
    napravi burek od mrkve.
    <br>
    došo zec četvrti dan i pita
    <br>
    imate bureka od mrkve?
    <br>
    pekar kaže-IMAMO!
    <br>
    kaže zec
    <br>
    -jel da je bljak!?
</body>
</html>
