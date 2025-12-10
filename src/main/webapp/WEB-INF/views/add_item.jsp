<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Item - Lost&Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/add_item.css">
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
<form action="${pageContext.request.contextPath}/addItem"
      method="post"
      enctype="multipart/form-data">
    <h2>Add New Item</h2>

    <label>Type:</label>
    <select name="type" required>
        <option value="lost">Lost</option>
        <option value="found">Found</option>
    </select>

    <label>Title:</label>
    <input type="text" name="title" required>

    <label>Description:</label>
    <textarea name="description"></textarea>

    <label>Location:</label>
    <input type="text" name="location" required>

    <label>Date (event):</label>
    <input type="datetime-local" name="event_date" required>

    <label>Category:</label>
    <select name="category_name" required>
        <c:forEach var="cat" items="${categories}">
            <option value="${cat}">${cat}</option>
        </c:forEach>
    </select>

    <label>Images (max 3):</label>
    <input type="file" name="images" accept="image/*" multiple>
    <div class="file-hint">You can upload up to 3 images</div>

    <button type="submit">Add Item</button>
</form>
</div>
</body>
</html>
