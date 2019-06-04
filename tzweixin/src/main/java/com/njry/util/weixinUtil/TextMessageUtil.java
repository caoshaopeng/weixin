package com.njry.util.weixinUtil;

import java.util.Date;
import com.njry.model.message.response.TextMessage;
import com.thoughtworks.xstream.XStream;

public class TextMessageUtil implements BaseMessageUtil<TextMessage> {
	/**
	 * 将发送消息封装成对应的xml格式
	 */
	public  String messageToxml(TextMessage  message) {
		XStream xstream  = new XStream();
		xstream.alias("xml", message.getClass());
		return xstream.toXML(message);
	}
	/**
	 * 封装发送消息对象,封装时，需要将调换发送者和接收者的关系
	 * @param FromUserName
	 * @param ToUserName
	 */
	public String initMessage(String FromUserName, String ToUserName) {
		TextMessage text = new TextMessage();
		text.setToUserName(FromUserName);
		text.setFromUserName(ToUserName);
		text.setContent("测试使用回复！");
		text.setCreateTime(new Date().getTime());
		text.setMsgType("text");
	    return messageToxml(text);
	}
	
	//添加封装发送消息的方法，重载，将内容传入
	public String initMessage(String FromUserName, String ToUserName,String Content) {
		TextMessage text = new TextMessage();
		text.setToUserName(FromUserName);
		text.setFromUserName(ToUserName);
		text.setContent("您输入的内容是："+Content);
		text.setCreateTime(new Date().getTime());
		text.setMsgType("text");
	    return  messageToxml(text);
	}
}
