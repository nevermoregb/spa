<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>SPASPASPA</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<script src="/common-js/code.jquery.com_jquery-3.7.0.min.js"></script>
	<script src="/common-js/fn_common.js"></script>
	<script src="https://unpkg.com/dropzone@5/dist/min/dropzone.min.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.css" />
	<link rel="stylesheet" href="https://unpkg.com/dropzone@5/dist/min/dropzone.min.css" type="text/css"/>
    <link rel="stylesheet" href="/adminLTE/bower_components/bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/adminLTE/bower_components/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="/adminLTE/bower_components/Ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="/adminLTE/dist/css/AdminLTE.min.css">
    <link rel="stylesheet" href="/adminLTE/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
	<script src="https://unpkg.com/swiper/swiper-bundle.js"></script>
    <script src="/adminLTE/bower_components/ckeditor/ckeditor.js"></script>
    <script src="/adminLTE/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=6f4212d48e014daf1292cb132a68e163&libraries=services,clusterer,drawing"></script>	<%--kakao map apikey --%>
    <style type="text/css">
	    .custom_pointer 	{ cursor:pointer; }
	    .fix_text_length 	{ overflow:hidden; text-overflow: ellipsis; white-space: nowrap; }
	    .custom_scrollbar   { height:800px; overflow:auto; }
	    .box-footer			{ border-top:none; }
	    .swiper-container 	{ width:500px;!important }
	    .custom_marginbot0 	{ margin-bottom:0px;}
    </style>
    
</head>
<!-- <!DOCTYPE html> -->
<!-- <html lang="ko"> -->
<!-- <head> -->
<!--     <meta charset="utf-8"> -->
<!--     <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"> -->
<!--     <meta name="description" content=""> -->
<!--     <meta name="author" content=""> -->
<!--     <title>로그인</title> -->
<!--    	<script src="/common-js/code.jquery.com_jquery-3.7.0.min.js"></script> -->
<!--     <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous"> -->
<!--     <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"> -->
<!-- </head> -->