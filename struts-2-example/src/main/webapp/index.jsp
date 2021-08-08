<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Basic Struts 2 Application - Welcome!</title>
</head>
<body>
<h1>Welcome to Struts 2!</h1>
<s:url action='controller01' var="helloGetLink">
<s:param name="userName">Bruce Strutsein</s:param>
<s:param name="id">1</s:param>
</s:url>
<p><a href="${helloGetLink}" >Redirection to Controller01Action with Get Param</a></p>

<div>

<s:url action='controller01' var="helloPostLink" />
<form action="${helloPostLink}" method="post" >
userName: <input type="text" name="userName" />
id (Estado): <input type="text" name="id" />
<input type="submit" value="enviar" />
</form>

</div>

</body>
</html>