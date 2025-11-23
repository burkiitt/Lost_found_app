<%@ page import="ru.relex.jakartaee_project.entity.Image" %>
<%@ page import="ru.relex.jakartaee_project.dto.ItemDto" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Login!!!</title>
</head>
<body>
<form action="/login" method="post">
    <label for="email">
        Email:
        <input type="text" name="email" id="email" value="${param.email != null ? param.email : ''}" />
    </label>

    <c:if test="${param.error == 'empty'}">
        <span style="color: red">Email and password are required</span>
    </c:if>

    <br />

    <label for="password">
        Password:
        <input type="password" name="password" id="password" />
    </label>

    <br />

    <button type="submit">Login</button>
    <a href="/registration"><button type="button">Register</button></a>

    <c:if test="${param.error == 'invalid'}">
        <div style="color: red">
            <span>Email or password incorrect</span>
        </div>
    </c:if>
</form>
</body>
</html>