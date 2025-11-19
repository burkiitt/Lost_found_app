<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Личный кабинет</title>
</head>
<body>
<h2>Добро пожаловать, ${user.name}!</h2>
<p><strong>Email:</strong> ${user.email}</p>
<!-- Здесь можно добавить дополнительные поля профиля или функции -->

<a href="/logout"><button type="button">Выйти</button></a>
</body>
</html>