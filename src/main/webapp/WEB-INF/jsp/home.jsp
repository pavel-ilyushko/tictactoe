<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html>
    <head>
        <title>Games</title>
    </head>

    <body>
        Games:
        <c:forEach items="${games}" var="game">
            <p><a href="${game.id}">${game}</a></p>
        </c:forEach>

        <a href="new">Add new</a>
    </body>
</html>