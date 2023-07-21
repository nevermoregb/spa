<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
	<tr>
		<th>번호</th>
		<th>사용자이메일</th>
		<th>비밀번호</th>
		<th>계급</th>
	<tr>
	<c:if test="${not empty memberList }">
		<c:forEach var="list" items="${memberList }">
			<tr>
				<td>${list.userIdx }</td>
				<td>${list.userId }</td>
				<td>${list.userPw }</td>
				<td>${list.userAuth }</td>
			</tr>
		</c:forEach>
	</c:if>
</table>
</body>
</html>