<%--
  Created by IntelliJ IDEA.
  User: Notebook
  Date: 16.10.2025
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>first JSP</title>
</head>
<body>
  <h1>Test JSP file</h1>
  <p>
      <%@ page import="java.util.Date,ru.relex.jakartaee_project.Test1" %>
      <%Test1 test = new Test1();%>
      <%=
            test.getInfo()
      %>
  </p>

</body>
</html>
