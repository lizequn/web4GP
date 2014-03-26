<%--
  Created by IntelliJ IDEA.
  User: cmdadmin
  Date: 25/03/14
  Time: 17:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
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