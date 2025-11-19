<%--
  Created by IntelliJ IDEA.
  User: Notebook
  Date: 22.10.2025
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add lost item </title>
    <link rel="stylesheet" href="style/main.css">

</head>
<body>
    <h1>Add a lost item</h1>
    <form action="add-item" method="post">
        <label>Item name:</label>
        <input type="text" name="item_name" required><br><br>

        <label>Date and Time:</label>
        <input type="datetime-local" name="time" required><br><br>

        <label>Place:</label>
        <input type="text" name="place" required><br><br>

        <label>Description:</label><br>
        <textarea name="description" rows="4" cols="40" required></textarea><br><br>

        <button type="submit">Submit</button>
    </form>
</body>
</html>
