package com.njry.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.njry.model.wxconfig.*;
import com.njry.util.weixinUtil.WeiXinUtil;

@RequestMapping(value = "/Config")
@Controller
public class ConfigController extends BaseController {
	
	@RequestMapping(value = "/getSignature.do")
	public void getSignature(HttpServletRequest request, HttpServletResponse response) {
		String url = req.getValue(request, "url");
		long timestamp = WeiXinUtil.getTimeStamp();
		String noncestr = WeiXinUtil.getNonceStr();
		String jsapi_ticket = WeiXinUtil.getJSApi_Ticket();
		String signature = WeiXinUtil.getSignature(timestamp, noncestr, url, jsapi_ticket);
		Config config = new Config();
		config.setAppId(WeiXinUtil.getAPPID());
		config.setNonceStr(noncestr);
		config.setTimestamp(timestamp);
		config.setSignature(signature);
		Map configMap = WeiXinUtil.getConfigMap(config);
		this.ajaxprint(response, 1, "获取成功!", configMap);
	}
	
	
}
