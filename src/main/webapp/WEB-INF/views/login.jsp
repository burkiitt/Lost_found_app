<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
</head>

<body>

<!-- NAVBAR -->
<div class="navbar">
    <div class="container">
        <h2 style="color:white; margin:0;">Lost&Found</h2>
        <div>
            <a href="/login">Login</a>
            <a href="/registration">Register</a>
        </div>
    </div>
</div>

<div class="container">
    <h1 class="page-title">Login to Your Account</h1>

    <form action="/login" method="post">

        <!-- Email -->
        <label for="email">Email</label>
        <input type="text"
               name="email"
               id="email"
               value="${param.email != null ? param.email : ''}"
               placeholder="example@mail.com">

        <!-- Empty error -->
        <c:if test="${param.error == 'empty'}">
            <div style="color:#FF4F4F; margin-bottom:10px;">
                Email and password are required
            </div>
        </c:if>

        <!-- Password -->
        <label for="password">Password</label>
        <input type="password" name="password" id="password" placeholder="Enter your password">

        <!-- Invalid error -->
        <c:if test="${param.error == 'invalid'}">
            <div style="color:#FF4F4F; margin-top:10px;">
                Email or password incorrect
            </div>
        </c:if>

        <!-- Buttons -->
        <button type="submit" class="btn" style="margin-top: 10px;">Login</button>
        <a href="/registration" class="btn-outline" style="margin-left:10px;">Register</a>

    </form>
</div>

</body>
</html>
