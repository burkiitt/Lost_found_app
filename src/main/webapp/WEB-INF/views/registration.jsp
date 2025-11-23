<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="style/registration.css">
</head>
<body>

<div class="form-container">
    <h2>Registration</h2>

    <form action="/registration" method="post">

        <label for="name">Name:
            <input type="text" name="name" id="name">
        </label>

        <label for="email">Email:
            <input type="text" name="email" id="email">
        </label>

        <label for="password">Password:
            <input type="password" name="password" id="password">
        </label>

        <input type="submit" value="Send">

        <c:if test="${not empty errors}">
            <ul class="error-list">
                <c:forEach var="err" items="${errors}">
                    <li>${err.message}</li>
                </c:forEach>
            </ul>
        </c:if>

    </form>
</div>

</body>
</html>
