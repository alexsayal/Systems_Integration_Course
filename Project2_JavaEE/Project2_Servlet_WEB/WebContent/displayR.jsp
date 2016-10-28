<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
body {
	font-family: "Palatino Linotype", "Book Antiqua", Palatino, serif;
	background-color: rgb(220, 230, 240);
}
</style>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Researcher DataBase</title>
</head>

<body>
	<p align="center"><strong>Welcome to the Researcher DataBase</strong></p>
	<p align="center">Version 1.0 - Project 2</p>

	<center><FORM>
	<INPUT TYPE="button" onClick="history.go(0)" VALUE="Refresh">
	</FORM></center>
	
	<br />
		
	<strong>The list of all researchers available (${numberResearchers})</strong> at ${today}:
	<c:forEach var="item" items="${myListOfResearchers}">
		<div> -- ${item}</div>
	</c:forEach>
	
</body>
</html>