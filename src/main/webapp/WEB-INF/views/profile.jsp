<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Личный кабинет - Lost&Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/profile.css">
</head>
<body>
<header>
    <h1>Lost&Found</h1>
    <div class="header-links">
        <a href="${pageContext.request.contextPath}/items">Все вещи</a>
        <a href="${pageContext.request.contextPath}/publications">мои поблукации</a>
        <a href="${pageContext.request.contextPath}/addItem">Добавить вещь</a>
    </div>
</header>
<div class="profile-container">
    <div class="profile-card">
        <div class="profile-header">
            <h2>Личный кабинет</h2>
            <p class="welcome-text">Добро пожаловать, ${user.fullName}!</p>
        </div>
        
        <div class="profile-info">
            <div class="info-item">
                <strong>Email:</strong>
                <span>${user.email}</span>
            </div>
        </div>
        
        <div class="profile-actions">
            <a href="${pageContext.request.contextPath}/logout">
                <button type="button">Выйти</button>
            </a>
        </div>
    </div>
</div>
</body>
</html>