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
<link rel="stylesheet" type="text/css" href="/amazeui/css/amazeui.css?1=3"/>
<script type="text/javascript" src="/amazeui/js/amazeui.js?1=3"></script>
<!-- 微信工具 -->
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
		<div>
			<button type="button" class="am-btn am-btn-success" id="imageUpdateBtn">绿色按钮</button>
		</div>
		<div id="images">
			
		</div>
	</div>
</body>
<script type="text/javascript">
	var wxUtil;
	$(function(){
		$('.loadimg').lazyload();
		
		wxUtil = new wxUtil({
			debug : false,
			appId : "wxf4275a64ac5d819d",
			hideMenuItems : [ 'menuItem:share:qq', //分享到QQ
		        'menuItem:share:weiboApp', // 分享到Weibo
		        'menuItem:favorite', // 收藏
		        'menuItem:share:QZone', // 分享到 QQ 空间
		        'menuItem:copyUrl', // 复制链接
		        'menuItem:openWithQQBrowser', // 在QQ浏览器中打开
		        'menuItem:openWithSafari', // 在Safari中打开
		        'menuItem:share:email', // 邮件
		        'menuItem:readMode', // 阅读模式
		        'menuItem:originPage' // 原网页
		        ]
		});
		
		wxUtil.onMenuShareAppMessage({
			title: '这是分享测试', // 分享标题
			desc: '这是分享测试', // 分享描述
			link: 'http://caoshaopeng.natapp1.cc/Test/frame.do', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
			imgUrl: '', // 分享图标
			type: '', // 分享类型,music、video或link，不填默认为link
			dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
			success: function () {
			// 用户点击了分享后执行的回调函数
				console.log("分享成功！");
			},
			cancel: function () {
				//用户取消分享后执行的回调函数
			}
		});
		
		wxUtil.onMenuShareTimeline({
			title: '分享朋友圈', // 分享标题
		    link: '', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
		    imgUrl: '', // 分享图标
		    success: function () {
		    // 用户点击了分享后执行的回调函数
			}
		});
	});
	
	$("#imageUpdateBtn").on("click", function(){
		wxUtil.chooseImage({
			count: 3, // 默认9
			sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
			success: function (res) {
				var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
				for(var i = 0; i < localIds.length; i++){
					$("#images").append("<img src="+localIds[i]+" style='width: 200px; height: 200px;'/>");
				}
			}
		});
	});
	
</script>
</html>