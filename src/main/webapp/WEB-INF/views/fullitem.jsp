<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.relex.jakartaee_project.dto.FullItemDto" %>
<%@ page import="ru.relex.jakartaee_project.entity.Image" %>

<html>
<head>
    <title>Item Details - Lost&Found</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/fullitem.css">
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
<div class="item-details-container">
    <div class="item-details-card">

    <%
        FullItemDto item = (FullItemDto) request.getAttribute("item");
    %>

        <div class="item-header">
            <h2><%= item.title() %></h2>
        </div>

        <div class="item-info">
            <div class="info-row">
                <b>Description:</b>
                <span><%= item.description() %></span>
            </div>
            <div class="info-row">
                <b>Location:</b>
                <span><%= item.location() %></span>
            </div>
            <div class="info-row">
                <b>Event Date:</b>
                <span><%= item.eventDate() %></span>
            </div>
            <div class="info-row">
                <b>Status:</b>
                <span><span class="status-badge <%= item.status().equals("active") ? "active" : "inactive" %>"><%= item.status() %></span></span>
            </div>
            <div class="info-row">
                <b>User:</b>
                <span><%= item.user().getName() %></span>
            </div>
            <div class="info-row">
                <b>User's email:</b>
                <span><%= item.user().getEmail() %></span>
            </div>
            <div class="info-row">
                <b>Category:</b>
                <span><%= item.category().getName() %></span>
            </div>
        </div>

        <div class="images-section">
            <h3>Images</h3>
    <div class="images">
        <%
            for (Image img : item.images()) {
        %>
        <img src="<%= request.getContextPath() %>/images/<%= item.id() %>/<%= img.getUrl() %>" alt="Item image">
        <%
            }
        %>
    </div>

        <a class="back-link" href="<%= request.getContextPath() %>/items">← Back to Items</a>
    </div>
</div>

</body>
</html>
