<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>로그인</title>
   	<script src="/common-js/code.jquery.com_jquery-3.7.0.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>
<div class="container">
    <form id="loginForm" class="form-signin" method="post" action="/loginProc">
        <p class="text-center">
<!--             <img src="/images/profile.png" class="img-thumbnail" style="width: 200px;" alt="이 글이 보인다면 시큐리티 설정 잘못한거임!"> -->
        </p>
        <h2 class="form-signin-heading text-center mb-5">로그인</h2>

        <p>
            <label for="username" class="sr-only">아이디</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="이메일" required="" autofocus="">
        </p>
        <p>
            <label for="password" class="sr-only">비밀번호</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="비밀번호" required="">
        </p>
        
        <button id="loginBtn" class="btn btn-lg btn-primary btn-block" type="button">로그인</button>
        <button id="joinMember" class="btn btn-lg btn-warning btn-block" type="button" onclick="javascript:location.href='/main/join'" >회원가입하기</button>
        
    </form>

    
</div>
<script type="text/javascript">
// $('#joinMember').on('click', function(){
// 	location.href="/main/join";
// });

var $post = function(url, form) {
	return $.ajax({
		type		: 'post',
		dataType	: 'json',
// 		contentType	: "application/json",
		url			: url,
		data		: form,
		success : function(data){
			alert(data);
		}
	})
	.done(function(data, xhr){
alert(data);
	})
	.fail(function(data){
	    if(data.responseCode) {
			console.log(data.responseCode);
			alert(data.responseCode);
	    }
	});
};




$('#loginBtn').on('click', function(){
// 	let formData = $('#loginForm').serialize();
	
// 	$.ajax({
// 		url: "/main/login",
// 		type: "post",
// 		data: formData,
// 		dataType: json
// 	});
	

// 	$post('/loginProc', formData);

	$('#loginForm').submit();
	
});



</script>
</body>
</html>