;!(function(window, $){
	
	var wxConfig = function(opt){
		var defaults = {
			debug : false,
			appId : '',
			jsApiList : ['checkJsApi',
		            'onMenuShareTimeline',
		            'onMenuShareAppMessage',
		            'onMenuShareQQ',
		            'onMenuShareWeibo',
		            'onMenuShareQZone',
		            'hideMenuItems',
		            'showMenuItems',
		            'hideAllNonBaseMenuItem',
		            'showAllNonBaseMenuItem',
		            'translateVoice',
		            'startRecord',
		            'stopRecord',
		            'onVoiceRecordEnd',
		            'playVoice',
		            'onVoicePlayEnd',
		            'pauseVoice',
		            'stopVoice',
		            'uploadVoice',
		            'downloadVoice',
		            'chooseImage',
		            'previewImage',
		            'uploadImage',
		            'downloadImage',
		            'getNetworkType',
		            'openLocation',
		            'getLocation',
		            'hideOptionMenu',
		            'showOptionMenu',
		            'closeWindow',
		            'scanQRCode',
		            'chooseWXPay',
		            'openProductSpecificView',
		            'addCard',
		            'chooseCard',
		            'openCard'],
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
			        ],
			share : null
		}
		
		this.options = $.extend({}, defaults, opt);
		this.getconfig();
	}
	
	wxConfig.prototype = {
		getconfig : function(){
			var that = this;
			var url = "/Config/getSignature.do?url="+encodeURIComponent(location.href.split('#')[0]);			
			
			$.ajax({
				type : "post",
				dataType : "json",
				url : url,
				success : function(data){
					that.setconfig(data);
					that.ready();
				}, error : function(){
					alert("公众号出现故障！");
				}
			});
		},
		setconfig : function(data){
			var that = this;
			wx.config({
		        debug: true, //
		        appId: data.others.appId, // 必填，公众号的唯一标识
		        timestamp: data.others.timestamp, // 必填，生成签名的时间戳
		        nonceStr: data.others.nonceStr, // 必填，生成签名的随机串
		        signature: data.others.signature,// 必填，签名，见附录1
		        jsApiList: that.options.jsApiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2		        
		    });		    
		},
		ready : function(){
			var that = this;
			wx.ready(function(){
				wx.hideMenuItems({
			      menuList: that.options.hideMenuItems
			    })
			});
		}, 
		onMenuShareAppMessage : function(res){
			wx.ready(function(){		
				wx.onMenuShareAppMessage($.extend({}, res));
			});
		},
		onMenuShareTimeline : function(res){
			wx.ready(function(){		
				wx.onMenuShareTimeline($.extend({}, res));
			});
		}
	}
	
	window.wxConfig = wxConfig;
	
}(window, $));