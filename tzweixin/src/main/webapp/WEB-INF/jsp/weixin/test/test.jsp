<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=no"/>
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="Cache-Control" content="no-siteapp">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta name="apple-mobile-web-app-title" content="微信网页端测试">
<title>微信网页端测试</title>
<script type="text/javascript" src="/application-js/jquery-3.2.1.js?f=d"></script>
<script type="text/javascript" src="/application-js/jquery_lazyload-1.7.2/jquery.lazyload.js?f=d"></script>
<!-- 移动端样式 -->
<link rel="text/css" href="layui/css/layui.css?1=3"/>
<script type="text/javascript" src="/layui/layui.js?1=3"></script>
<script type="text/javascript" src="/application-js/jweixin-1.4.0.js?1=3"></script>
<script type="text/javascript" src="/application-js/jweixin-util.js?2=3"></script>
<style type="text/css">
	.div_1{
		width: 100%;
		text-align: center;
	}
</style>
</head>
<body>
	<div class="div_1">
		<header style="height: 30px;"></header>
		<img alt="111" class="loadimg" src="/application-images/loadimg.gif" data-original="/images/test/bg.png" style="width: 200px; height: 200px;">
	</div>
</body>
<script type="text/javascript">
	$('.loadimg').lazyload();
	
	var wxConfig = new wxConfig();
	
</script>
</html>