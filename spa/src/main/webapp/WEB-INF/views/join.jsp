<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>회원가입</title>
	<script src="/common-js/code.jquery.com_jquery-3.7.0.min.js"></script>
	<script src="/common-js/fn_common.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
</head>
<body>
	<div class="container">
	    <form class="form-signin" id='join_form'>
	        <h2 class="form-signin-heading text-center mb-5">회원가입</h2>
	        <p>
	            <label for="userEmail" class="sr-only">이메일</label>
	            <input type="text" id="userEmail" name="userEmail" class="form-control removeWhiteSpace" placeholder="이메일" required autofocus="">
	            <button type="button" id="sendEmailCode" >이메일 인증</button> 
	        </p>
	        <p id="p_code" style="display:none;">
	            <input type="text" id="inputCode" name="inputCode" class="form-control removeWhiteSpace" placeholder="코드입력" required>
	            <button type="button" id="checkEmailCode" >코드확인</button>
	        </p>
	        <p>
		        <label for="userName" class="sr-only">닉네임</label>
	            <input type="text" id="userName" name="userName" class="form-control removeWhiteSpace" placeholder="닉네임" required>
	        </p>
	        <p>
	            <label for="password" class="sr-only">비밀번호</label>
	            <input type="password" id="password" class="form-control removeWhiteSpace" placeholder="비밀번호" required>
	        </p>
	        <p>
	            <label for="password" class="sr-only">비밀번호</label>
	            <input type="password" id="password2" class="form-control removeWhiteSpace" placeholder="비밀번호 확인" required>
	        </p>
	        
	        <button id="joinMember" class="btn btn-lg btn-primary btn-block" type="button">회원가입</button>
	    </form>
	</div>
	
	<form id="dataForm">
		<input type="hidden" name="userId" >
		<input type="hidden" name="userName" >
		<input type="hidden" name="userPw" >
	</form>

<script>
<%-- 비밀번호 유효성 체크 --%>
const validation = function(){
	let pass1 = $('#password').val();
	let pass2 = $('#password2').val();
	let userName = $('#userName').val();
	let email = $('[name=userId]').val();
	let result = false;
	
	if(email == ''){
		alert("이메일 인증을 진행해 주세요.");
		return false;
	}
	
	if(userName != ''){
		$('[name=userName]').val(userName);
	} else {
		alert("닉네임을 입력해 주세요.");
		return false;
	}
	
	if(pass1 != '' && pass2 != ''){
		if(pass1 == pass2) {
			$('[name=userPw]').val(pass1);
			result = true;
		} else {
			alert("비밀번호와 비밀번호 확인에 같은 값을 입력해 주세요");
			return false;
		}
	} else {
		alert("비밀번호를 입력해 주세요.");
		return false;
	}
	
	return result;
};

<%-- 입력한 이메일로 확인메일 전송 --%>
$('#sendEmailCode').on('click', function(){
	if(emailValid($('#userEmail'))){
		let email = {'userEmail' : $('#userEmail').val()};
		
		$post('/main/checkMail', email)
		
		.done(function(data, xhr){
			if(data.result) {
				alert(data.msg);
				$('#sendEmailCode').prop('hidden', true);
				$('#p_code').attr('style', 'display:block');
			} else {
				alert(data.msg);
			}
		});
	}
});

<%-- 메일로 받은 코드 확인 --%>
$('#checkEmailCode').on('click', function(){
	let code = $('#inputCode').val().trim();
	let data = {"code" : code};
	
	if(code != ""){
		$post('/main/checkCode', data)
		
		.done(function(data){
			if(data.result) {
				alert(data.msg);
				$('[name=userId]').val($('#userEmail').val());
				$('#inputCode').prop('disabled', true);
				$('#userEmail').prop('disabled', true);
				$('#checkEmailCode').prop('hidden', true);
				
			} else {
				alert(data.msg);
			}
		});
	} else {
		alert("메일로 전송된 코드를 입력해 주세요");
	}
});

<%-- 회원가입 ajax--%>
$('#joinMember').on('click', function(){
	if(validation()) {
		let data = $('#dataForm').serialize();
		
		$post('/main/joinMember', data)
		
		.done(function(data){
			if(data.result) {
				alert(data.msg);
				location.href = "/main/login";
			} else {
				alert(data.msg);
			}
		});
	}
});

</script>
</body>
</html>