<%--
  Created by IntelliJ IDEA.
  User: adrien
  Date: 2019-04-19
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/home/topReceiversByPacketsIn"
      method="get">
    Enter the date from 07-13-2018 to 07-25-18
    <br><br>
    <input type="text" name="date"
           placeholder="format date MM-dd-yyyy">
    <input type="submit">

</form>
</body>
</html>
