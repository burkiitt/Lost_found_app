<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Item</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/addItem"
      method="post"
      enctype="multipart/form-data">

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

    <button type="submit">Add Item</button>
</form>

</body>
</html>
