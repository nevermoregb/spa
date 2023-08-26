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
    <link href="/common-css/custom_common_css.css" rel="stylesheet" />
    <style>
    	.hide{ display:none; }
    </style>
</head>
<body>
	<div class="container">
	    <form class="form-signin" id='join_form'>
	        <h2 class="form-signin-heading text-center mb-5">비밀번호 재설정</h2>
	        <p>
	            <label for="userEmail" class="sr-only" >이메일</label>
	            <input type="text" id="userEmail" name="userEmail" class="form-control removeWhiteSpace" placeholder="이메일" required autofocus="">
	            <button type="button" class="btn btn-primary" id="sendEmailCode" 	>이메일 인증</button> 
	            <button type="button" class="btn btn-primary hide" id="resetEmail" 	>이메일 재설정</button>
	        </p>
	        <p id="p_code" class="hide" >
	        	<label style="position: relative; width:300px;">
		            <input type="text" style="z-index:1;" id="inputCode" name="inputCode" class="form-control removeWhiteSpace" placeholder="코드입력" required>
		            <button type="button" style="border: none; position: absolute; bottom: 0; right: 5px; color:red; background-color:transparent; z-index:1;" id="counter" ></button>
	            </label>
	            <button type="button" class="btn btn-primary" id="checkEmailCode" 		>코드확인</button>
	            <button type="button" class="btn btn-primary hide" id="timerReset" 		>시간연장</button>
	        </p>
	        <p>
	            <label for="password" class="sr-only">비밀번호</label>
	            <input type="password" id="password" class="form-control removeWhiteSpace" placeholder="비밀번호" required>
	        </p>
	        <p>
	            <label for="password" class="sr-only">비밀번호</label>
	            <input type="password" id="password2" class="form-control removeWhiteSpace" placeholder="비밀번호 확인" required>
	        </p>
	        
	        <button id="chagePassword" class="btn btn-lg btn-primary btn-block" type="button">비밀번호 변경</button>
	        
     	    <div class="cus_right_align"> 
				<a class="cus_font_santo" href="/main/login">< 뒤로가기</a>
			</div>
	    </form>
	</div>
	
	<form id="dataForm">
		<input type="hidden" name="userId" >
		<input type="hidden" name="userPw" >
	</form>

<script>
<%-- 비밀번호 유효성 체크 --%>
const validation = function(){
	let pass1 		= $('#password').val();
	let pass2 		= $('#password2').val();
	let email 		= $('[name=userId]').val();
	let result 		= false;
	
	if(email == ''){
		alert("이메일 인증을 진행해 주세요.");
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
		
		$post('/main/checkMail2', email)
		
		.done(function(data, xhr){
			if(data.result) {
				timerFn(70, '#counter');
				alert(data.msg);
				$('#sendEmailCode').addClass('hide');
				$('#resetEmail, #p_code').removeClass('hide');
				$('#userEmail').prop('disabled', true);
// 				$('#p_code').attr('style', 'display:block');
				$('#checkEmailCode, #timerReset').prop('disabled', false);
				$('#inputCode').val('');
			} else {
				alert(data.msg);
			}
		});
	}
});

<%--이메일 재설정--%>
$('#resetEmail').on('click', function(){
	let email = {'userEmail' : $('#userEmail').val()};
	
	$post('/main/cancelMail', email)
	.done(function(data, xhr){
		if(data){
			timerFn(null, '#counter');
			alert('다시 인증을 진행해 주세요.');
			$('#userEmail').val('');
			$('#sendEmailCode').removeClass('hide');
			$('#resetEmail, #timerReset, #p_code').addClass('hide');
			$('#userEmail').prop('disabled', false);
// 			$('#p_code').attr('style', 'display:none');
			onlyOne = false;
		} else {
			alert(data.msg);
		}
		
	});
})

<%-- 메일로 받은 코드 확인 --%>
$('#checkEmailCode').on('click', function(){
	let code = $('#inputCode').val().trim();		<%--코드입력값--%>
	let data = {"code" : code};
	
	if(code != ""){
		$post('/main/checkCode', data)
		
		.done(function(data){
			if(data.result) {
				alert(data.msg);
				timerFn(null, '#counter');
				$('[name=userId]').val($('#userEmail').val());
				$('#inputCode, #userEmail').prop('disabled', true);
				$('#resetEmail, #counter, #timerReset').addClass('hide');
				$('#checkEmailCode').prop('hidden', true);
				
			} else {
				alert(data.msg);
			}
		});
	} else {
		alert("메일로 전송된 코드를 입력해 주세요");
	}
});

<%-- 타이머 시간연장 버튼--%>
$('#timerReset').on('click', function(){
	let email = {'userEmail' : $('#userEmail').val()};
	$post('/main/extendTime', email)
	.done(function(callBack){
		if(callBack) {
			timerFn(175, '#counter', true);
			$('#timerReset').addClass('hide');
			onlyOne = true;
		}
	});
});

<%-- 비밀번호 재설정 ajax--%>
$('#chagePassword').on('click', function(){
	if(validation()) {
		let data = $('#dataForm').serialize();
		
		$post('/main/chagePassword', data)
		
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

<%--타이머 세팅변수--%>
let timer 		= null;
let isRunning 	= false;
let onlyOne		= false;

<%-- 타이머 버튼 클릭(시작, 재시작)--%>
const timerFn = function(sec, view, reset){	
	let display 		= $(view);
	let setSeconds		= sec;			<%--타이머 시간 세팅--%>
	let setButtonsPopup = ['timerReset', 'checkEmailCode'];		<%--버튼 노출 리스트--%> 
	
	<%--타이머 종료--%>
	if(isRunning){
		clearInterval(timer);			
		isRunning = false;
		(reset) ? startTimer(setSeconds, display, setButtonsPopup) : display.html("");	<%--시간연장 버튼 클릭 시 타이머 재시작--%>
	<%--타이머 시작--%>	
	} else {
		startTimer(setSeconds, display, setButtonsPopup);
	}
};

<%--타이머 시작--%>    
const startTimer = function(count, display, btnControl) {  
	timer = setInterval(function () {
	    let minutes = parseInt(count / 60, 10);
	    let seconds = parseInt(count % 60, 10);
	
	    minutes = minutes < 10 ? "0" + minutes : minutes;
	    seconds = seconds < 10 ? "0" + seconds : seconds;
	
	    display.html(minutes + ":" + seconds);
	
	  	<%--타이머 1분 이하일때 시간연장 버튼 노출(한번만 연장 가능)--%>
	    if(onlyOne == false && minutes < 1) {
	    	
			<%-- 시간연장 버튼--%>
			$('#' + btnControl[0]).removeClass('hide');
	    }
	    
	    <%--타이머 시간만료--%>
	    if (--count < 60) {
			clearInterval(timer);
			isRunning = false;
			onlyOne = false;
			display.html("시간초과");
			
			<%-- 타이머 만료시 disabled 처리된 버튼들 --%>
			$(btnControl).each(function() {
			    $('#' + this).attr('disabled', true); 
		    });
	    }
	}, 1000);
	
	isRunning = true;
};

</script>
</body>
</html>