<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello World JSP!</title>
</head>
<body>
<h2><s:property value="messageStore.message" /></h2>

<p>The userName sent is <b><s:property value="userName" /></b></p>

<p>The Estate id sent is <b><s:property value="id" /></b></p>

<p>The Estate name retrieved from DB is <b><s:property value="nombre" /></b></p>
</body>
</html>