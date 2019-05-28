;!(function($, window){
	
	function getWidth(){
		var width = $(window).width();
		var font_size = width / 20;
		font_size = font_size > 24 ? 24 : font_size;
		return font_size;
	}
	
	window.onload = function(){
		$("html").hide();
		$("html").css("font-size",getWidth()+"px");
		$("html").show();
	}
	
	window.onresize = function(){
		$("html").css("font-size",getWidth()+"px");
	}
})($, window);