<%@ page import="ru.relex.jakartaee_project.entity.Image" %>
<%@ page import="ru.relex.jakartaee_project.dto.ItemDto" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список вещей - Lost&Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/items.css">
</head>
<body>
<!-- Header -->
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

<!-- Search bar -->
<form class="search-bar" method="get" action="SearchItemsServlet">
    <input type="text" name="name" placeholder="Введите имя вещи"/>

    <label>Category:</label>
    <select name="category_name">
        <option value="">Все категории</option>
        <c:forEach var="cat" items="${categories}">
            <option value="${cat}">${cat}</option>
        </c:forEach>
    </select>

    <select name="type">
        <option value="">Все типы</option>
        <option value="lost">Lost</option>
        <option value="found">Found</option>
    </select>

    <input type="date" name="dateFrom"/>
    <input type="date" name="dateTo"/>
    <input type="submit" value="Поиск"/>
</form>

<!-- Items list -->
<ul>
    <%
        List<ItemDto> items = (List<ItemDto>) request.getAttribute("items");
        if (items != null) {
            for (ItemDto item : items) {
    %>
    <li class="item-card">
        <a href="<%= request.getContextPath() %>/full_Item?id=<%= item.id() %>">
            <b>Title:</b> <%= item.title() %><br>
            <b>Description:</b> <%= item.description() %><br>

            <b>Images:</b><br>
            <div class="item-images">
                <%
                    for (Image img : item.images()) {
                %>
                <img src="<%= request.getContextPath() %>/images/<%= item.id() %>/<%= img.getUrl() %>" alt="Item image">
                <%
                    }
                %>
            </div>
        </a>
    </li>
    <%
            }
        }
    %>
</ul>
</body>
</html>
