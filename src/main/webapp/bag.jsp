<%--
  Created by IntelliJ IDEA.
  User: Notebook
  Date: 18.10.2025
  Time: 19:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bag items</title>
</head>
<body>
    <%@ page import="ru.relex.jakartaee_project.Test_bag.Bag,java.util.List,java.util.ArrayList" %>
    <%
      List<Bag> list = (List<Bag>) session.getAttribute("list");
      if(list!=null & !list.isEmpty()){
        for (Bag bag:list){
    %>
      <p>name : <%=bag.getName()%> price : <%=bag.getPrice()%>></p>
    <%  
        }
      }
      else{
    %>
      <p>No items added yet.</p>
    <%
      }
    %>
</body>
</html>
