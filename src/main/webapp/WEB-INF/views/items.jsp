<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Items</title></head>
<body>
<%@include file="header.jsp"%>
<h2>Items list</h2>

<c:forEach var="item" items="${items}">
    <div style="border:1px solid #ccc; padding:10px; margin:10px">
        <h3>${item.title}</h3>
        <p>${item.description}</p>

        <c:forEach var="img" items="${item.images}">
            <img src="${img.url}" width="200" style="margin:5px"/>
        </c:forEach>
    </div>
</c:forEach>

</body>
</html>
