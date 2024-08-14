<%@page import="com.test.model1board.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> <!-- fmt=format 약자 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리스트</title>

</head>
<body>
<div id="main">
<table>
<tr><th>번호</th><th>제목 작성자 작성일 조회수</th></tr>
<c:forEach var="b" items="${boards}" varStatus="status">
	<tr>
	<td><a href="boardDetail/${b.getBnum()}"><div> ${b.getBnum()}</div></td>	
	<td><div> ${b}</div></td>	
	</tr>
</c:forEach>

</table>

</div>
</body>
</html>