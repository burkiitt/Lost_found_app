<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.relex.jakartaee_project.dto.ItemDto" %>
<%@ page import="ru.relex.jakartaee_project.entity.Image" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>Items</title>
    <style>
        .item-card {
            border: 1px solid #ccc;
            padding: 10px;
            margin: 10px;
            list-style: none;
        }
        .item-images img {
            width: 200px;
            margin: 5px;
        }
        a {
            color: inherit;
            text-decoration: none;
        }
    </style>
</head>
<body>

<h2>Items List</h2>

<a href="${pageContext.request.contextPath}/profile">Личный кабинет</a>

<a href="${pageContext.request.contextPath}/addItem">add item</a>

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
                <img src="<%= img.getUrl() %>" alt="Item image">
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
