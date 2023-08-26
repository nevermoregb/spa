<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%    
// 뒤로가기 시 '양식 다시 제출 확인' 화면 안나오게
	response.setHeader("Cache-Control","no-store");    
	response.setHeader("Pragma","no-cache");    
	response.setDateHeader("Expires",0);    
	if (request.getProtocol().equals("HTTP/1.1"))  
	        response.setHeader("Cache-Control", "no-cache");  
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>로그인</title>
   	<script src="/common-js/code.jquery.com_jquery-3.7.0.min.js"></script>
   	<script src="/common-js/fn_common.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
    <link href="/common-css/custom_common_css.css" rel="stylesheet" />
</head>

<body>
<div class="container">
    <form id="loginForm" class="form-signin" method="post" action="/loginProc">
        <h2 class="form-signin-heading text-center mb-5">로그인</h2>

        <p>
            <label for="username" class="sr-only">아이디</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="이메일" data-error="이메일을" autofocus="" value="${username }">
        </p>
        <p>
            <label for="password" class="sr-only">비밀번호</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="비밀번호" data-error="비밀번호를" value="${password }">
        </p>
        
        <c:if test="${not empty errorMessage }">
        	<p style="color:red; font-weight:bold;">${errorMessage }</p>
        </c:if>
        
<%--         <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION }"> --%>
<%-- 			<p style="color:red; font-weight:bold;">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message }</p> --%>
<%-- 			<c:remove var="SPRING_SECURITY_LAST_EXCEPTION" scope="session" /> --%>
<%-- 		</c:if> --%>
		<div>
        	<button id="loginBtn" class="btn btn-lg btn-primary btn-block" type="button">로그인</button>
			<div class="cus_right_align"> 
				<a class="cus_font_santo" href="/main/passwordReset"					>비밀번호 재설정</a>
				<a class="cus_font_santo" style="margin-left: 16px;" href="/main/join"	>회원가입하기</a>
			</div>
        </div>
        
    </form>

    
</div>

<script type="text/javascript">

<%--로그인--%>
$('#loginBtn').on('click', function(){
	let targetInputs = ['username', 'password'];
	
	if(inputValid(targetInputs)){
		$('#loginForm').submit();
	}
});
</script>

</body>
</html>