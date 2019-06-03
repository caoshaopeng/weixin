package com.njry.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.njry.service.utilservice.WeiXinUtilService;
import com.njry.util.weixinUtil.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 与微信对接登录验证
 * @author caoshaopeng
 *
 */
@Controller
@RequestMapping(value="wxcertification/")
@Slf4j
public class WXCertification extends BaseController {
	
	@Autowired
	ImageMessageUtil util;
	
	@RequestMapping(value = "certification.do", method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		log.info("微信接入成功！");
		String signature = req.getValue(request, "signature");
		String timestamp = req.getValue(request, "timestamp");
		String nonce = req.getValue(request, "nonce");
		String echostr = req.getValue(request, "echostr");
		PrintWriter pw = null;
		
		if(WxCheckHelper.checkSignature(signature, timestamp, nonce)) {
			try {
				pw = response.getWriter();
				pw.write(echostr);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				pw.flush();
				pw.close();
			}
		}
	}
	
	@RequestMapping(value = "certification.do",method=RequestMethod.POST)
	public void doPost(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		//将微信请求xml转为map格式，获取所需的参数
		Map<String,String> map = MessageUtil.xmlToMap(request);
		log.info(map.toString());
		String ToUserName = map.get("ToUserName");
		String FromUserName = map.get("FromUserName");
		String MsgType = map.get("MsgType");
		String Content = map.get("Content");
		
		String message = null;
		//处理文本类型，实现输入1，回复相应的封装的内容
		if("text".equals(MsgType)){
			if("1".equals(Content)){
				TextMessageUtil textMessage = new TextMessageUtil();
				message = textMessage.initMessage(FromUserName, ToUserName);
			} else if(Content.equals("3")) {
				message = util.initMessage(FromUserName, ToUserName);
			} else {
				TextMessageUtil textMessage = new TextMessageUtil();
				message = textMessage.initMessage(FromUserName, ToUserName,Content);
			}
		} else {
			TextMessageUtil textMessage = new TextMessageUtil();
			message = textMessage.initMessage(FromUserName, ToUserName);
		}
		try {
			out = response.getWriter();
			out.write(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.close();
	}
}
