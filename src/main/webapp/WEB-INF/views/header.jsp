<%--
  Created by IntelliJ IDEA.
  User: Notebook
  Date: 19.11.2025
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:if test="${not empty sessionScope.user}">
        <form action="${pageContext.request.contextpath}/logout" method="post">
            <button type="submit">Logout</button>
        </form>
    </c:if>
</div>
