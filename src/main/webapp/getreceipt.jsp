<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.UUID" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: cmdadmin
  Date: 25/03/14
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    if(request.getSession().getAttribute("uuid") == null){
        response.sendRedirect("error.jsp");
    }
    List<UUID> list = (List<UUID>) request.getSession().getAttribute("uuid");
    System.out.println(list);
    for(UUID uuid: list){

%>
    <%=uuid%>
    <br>
<%
    }
%>
<form action="RegisterServlet" method="post">
    your email:
    <input type="text" name="email"  />
    <br />
    target email:
    <input type="text" name="toAddress"  />
    <br>
    <input type="submit" value="Upload File" />
</form>

</body>
</html>