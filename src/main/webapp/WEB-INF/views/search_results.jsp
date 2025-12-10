<%@ page import="ru.relex.jakartaee_project.entity.Image" %>
<%@ page import="ru.relex.jakartaee_project.dto.ItemDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Результаты поиска - Lost&Found</title>
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

<div style="padding: 20px;">
    <h2>Результаты поиска по изображению</h2>
    
    <%
        List<ItemDto> items = (List<ItemDto>) request.getAttribute("items");
        Map<Long, Double> scores = (Map<Long, Double>) request.getAttribute("scores");
        String error = (String) request.getAttribute("error");
        String noResultsMessage = (String) request.getAttribute("noResultsMessage");
        Double threshold = (Double) request.getAttribute("threshold");
        
        if (error != null) {
    %>
        <div style="color: red; padding: 10px; border: 1px solid red; margin: 10px 0; border-radius: 5px;">
            <%= error %>
        </div>
    <%
        } else if (noResultsMessage != null) {
    %>
        <div style="color: #856404; background-color: #fff3cd; padding: 15px; border: 1px solid #ffeaa7; margin: 10px 0; border-radius: 5px;">
            <strong>Результаты не найдены</strong><br>
            <%= noResultsMessage %>
        </div>
    <%
        } else if (items == null || items.isEmpty()) {
    %>
        <div style="color: #856404; background-color: #fff3cd; padding: 15px; border: 1px solid #ffeaa7; margin: 10px 0; border-radius: 5px;">
            <strong>Похожих изображений не найдено</strong><br>
            Попробуйте загрузить другое изображение или проверьте, есть ли в базе данных похожие предметы.
        </div>
    <%
        } else {
    %>
        <div style="background-color: #e7f3ff; padding: 10px; border-left: 4px solid #2196F3; margin: 10px 0; border-radius: 5px;">
            <strong>Найдено похожих изображений: <%= items.size() %></strong>
            <% if (threshold != null) { %>
                <br><small>Показаны только элементы со схожестью ≥ <%= String.format("%.0f%%", threshold * 100) %></small>
            <% } %>
        </div>
        
        <!-- Items list -->
        <ul>
            <%
                for (ItemDto item : items) {
                    Double score = scores != null ? scores.get(item.id()) : null;
            %>
            <li class="item-card">
                <a href="<%= request.getContextPath() %>/full_Item?id=<%= item.id() %>">
                    <% if (score != null) { 
                        // Color code based on similarity score
                        String bgColor;
                        if (score >= 0.8) {
                            bgColor = "#4CAF50"; // Green for high similarity
                        } else if (score >= 0.65) {
                            bgColor = "#2196F3"; // Blue for medium-high similarity
                        } else {
                            bgColor = "#FF9800"; // Orange for medium similarity
                        }
                    %>
                        <div style="float: right; background: <%= bgColor %>; color: white; padding: 5px 10px; border-radius: 5px; font-weight: bold;">
                            Схожесть: <%= String.format("%.1f%%", score * 100) %>
                        </div>
                    <% } %>
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
            %>
        </ul>
    <%
        }
    %>
    
    <a href="${pageContext.request.contextPath}/searchByImage" style="display: inline-block; margin-top: 20px; padding: 10px 20px; background: #007bff; color: white; text-decoration: none; border-radius: 5px;">
        Новый поиск
    </a>
</div>
</body>
</html>
