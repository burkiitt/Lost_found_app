<%@ page import="ru.relex.jakartaee_project.entity.Image" %>
<%@ page import="ru.relex.jakartaee_project.dto.ItemDto" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Login - Lost&Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/login.css">
</head>
<body>
<div class="login-container">
<form action="/login" method="post">
    <h2>Welcome Back</h2>
    <label for="email">
        Email:
        <input type="text" name="email" id="email" value="${param.email != null ? param.email : ''}" />
    </label>

    <c:if test="${param.error == 'empty'}">
        <div class="error-message">Email and password are required</div>
    </c:if>

    <br />

    <label for="password">
        Password:
        <input type="password" name="password" id="password" />
    </label>

    <br />

    <div class="button-group">
        <button type="submit">Login</button>
        <a href="/registration"><button type="button">Register</button></a>
    </div>

    <c:if test="${param.error == 'invalid'}">
        <div class="error-message">Email or password incorrect</div>
    </c:if>
</form>
</div>
</body>
</html>