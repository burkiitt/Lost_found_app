<%@ page import="ru.relex.jakartaee_project.entity.Image" %>
<%@ page import="ru.relex.jakartaee_project.dto.ItemDto" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Мои публикации - Lost&Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/publications.css">
</head>
<body>
<!-- Header -->
<header>
    <h1>Lost&Found</h1>
    <div class="header-links">
        <a href="${pageContext.request.contextPath}/items">Все вещи</a>
        <a href="${pageContext.request.contextPath}/profile">Личный кабинет</a>
        <a href="${pageContext.request.contextPath}/addItem">Добавить вещь</a>
    </div>
</header>

<!-- Publications list -->
<h2 style="text-align: center; color: white; margin: 20px 0; font-size: 28px;">Мои публикации</h2>

<ul>
    <%
        List<ItemDto> items = (List<ItemDto>) request.getAttribute("itemList");
        if (items != null && !items.isEmpty()) {
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
        <form method="post" action="<%= request.getContextPath() %>/publications">
            <input type="hidden" name="itemId" value="<%= item.id() %>">
            <button type="submit">Delete</button>
        </form>
    </li>
    <%
            }
        } else {
    %>
    <div class="empty-state">
        <h2>У вас пока нет публикаций</h2>
        <p>Начните добавлять вещи, чтобы они появились здесь</p>
        <a href="${pageContext.request.contextPath}/addItem" style="color: white; text-decoration: underline; margin-top: 20px; display: inline-block;">Добавить вещь</a>
    </div>
    <%
        }
    %>
</ul>
</body>
</html>
