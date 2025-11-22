<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.relex.jakartaee_project.dto.FullItemDto" %>
<%@ page import="ru.relex.jakartaee_project.entity.Image" %>

<html>
<head>
    <title>Item Details</title>
    <style>
        .container {
            width: 700px;
            margin: 20px auto;
            font-family: Arial, sans-serif;
        }
        .images img {
            width: 250px;
            margin: 10px;
        }
        .back {
            margin-top: 20px;
            display: inline-block;
        }
    </style>
</head>
<body>

<div class="container">

    <%
        FullItemDto item = (FullItemDto) request.getAttribute("item");
    %>

    <h2><%= item.title() %></h2>

    <p><b>Description:</b> <%= item.description() %></p>
    <p><b>Location:</b> <%= item.location() %></p>
    <p><b>Event Date:</b> <%= item.eventDate() %></p>
    <p><b>Status:</b> <%= item.status() %></p>
    <p><b>User:</b> <%= item.user() %></p>
    <p><b>Category:</b> <%= item.category() %></p>

    <h3>Images:</h3>
    <div class="images">
        <%
            for (Image img : item.images()) {
        %>
        <img src="<%= img.getUrl() %>" alt="Item image">
        <%
            }
        %>
    </div>

    <a class="back" href="<%= request.getContextPath() %>/items">← Back to Items</a>

</div>

</body>
</html>
