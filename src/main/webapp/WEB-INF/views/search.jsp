<%--
  Created by IntelliJ IDEA.
  User: Notebook
  Date: 27.11.2025
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/items.css">
</head>
<body>
<header>
    <h1>Lost&Found</h1>
    <div class="header-links">
        <a href="${pageContext.request.contextPath}/items">Главная</a>
        <a href="${pageContext.request.contextPath}/publications">мои поблукации</a>
        <a href="${pageContext.request.contextPath}/addItem">Добавить вещь</a>
        <a href="${pageContext.request.contextPath}/searchByImage">Поиск по изображению</a>
        <a href="${pageContext.request.contextPath}/profile">Личный кабинет</a>

    </div>
</header>
<div class="add-item-container">

<form action="searchByImage" method="post" enctype="multipart/form-data">
    <input type="file" name="photo" accept="image/*" required>
    <button type="submit">Search similar</button>
</form>
</div>
</body>
</html>
