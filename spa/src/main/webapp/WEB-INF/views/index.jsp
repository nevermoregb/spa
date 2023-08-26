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
		<th>제목</th>
		<th>작성자</th>
		<th>작성일</th>
		<th>조회수</th>
	<tr>
	<c:if test="${not empty boardList }">
		<c:forEach var="list" items="${boardList.getList() }">
			<tr>
				<td>${list.brdIdx }</td>
				<td>${list.title }</td>
				<td>${list.userName }</td>
				<td>${list.regDate }</td>
				<td>${list.viewCount }</td>
			</tr>
		</c:forEach>
	</c:if>
</table>
</body>
</html>