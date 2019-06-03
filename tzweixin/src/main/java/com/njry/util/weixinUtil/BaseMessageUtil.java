package com.njry.util.weixinUtil;

import org.springframework.stereotype.Component;

public interface BaseMessageUtil <T>{
	/**
	 * 将回复的信息对象转xml格式给微信
	 * @param message
	 * @return
	 */
	public abstract String messageToxml(T t);
	
	/**
	 * 回复的信息封装
	 * @param FromUserName
	 * @param ToUserName
	 * @param Content
	 * @return
	 */
	public abstract String initMessage(String FromUserName,String ToUserName);
}
