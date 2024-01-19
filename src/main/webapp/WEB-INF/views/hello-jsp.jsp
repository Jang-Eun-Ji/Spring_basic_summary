<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>screen</title>
</head>
<body>
<p>data(EL문법) : ${myData}</p>
<p>data(java 코드) :
<%
String getData = (String)request.getAttribute("myData");
out.print(getData);

 %> </p>
</body>
</html>