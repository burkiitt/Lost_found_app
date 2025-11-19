<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Notebook
  Date: 19.11.2025
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login!!!</title>
</head>
<body>
<form action="/login" method="post">
    <label for="email">Email:
        <input  type="text" name="email" id="email">
    </label><br>
    <label for="password">Password:
        <input  type="password" name="password" id="password">
    </label><br>
    <button type="submit">Login</button>>
    <a href="/registration">
        <button type="button">Register</button>
    </a>
    <c:if test="${param.error!=null}">
        <div style="color: red">
            <span>Email or password incorrect</span>
        </div>
    </c:if>
</form>
</body>
</html>
