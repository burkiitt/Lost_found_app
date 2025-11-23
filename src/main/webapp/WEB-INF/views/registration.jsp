<%@ page import="ru.relex.jakartaee_project.entity.Image" %>
<%@ page import="ru.relex.jakartaee_project.dto.ItemDto" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>registr please</title>
    <link rel="stylesheet" href="style/registration.css">
</head>
<body>
<form action="/registration" method="post">
    <label for="name">
        Name:
        <input type="text" name="name" id="name" />
    </label>
    <br />

    <label for="email">
        Email:
        <input type="text" name="email" id="email" />
    </label>
    <br />

    <label for="password">
        Password:
        <input type="password" name="password" id="password" />
    </label>
    <br />

    <input type="submit" value="Send" />

    <c:if test="${not empty errors}">
        <ul style="color:red">
            <c:forEach var="err" items="${errors}">
                <li>${err.message}</li>
            </c:forEach>
        </ul>
    </c:if>
</form>
</body>
</html>