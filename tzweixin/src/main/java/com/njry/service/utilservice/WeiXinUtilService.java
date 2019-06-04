package com.njry.service.utilservice;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public interface WeiXinUtilService {
	
	//插入accesstoken进入数据库
	public int insertAccessToken(String accessToken, String time, String appid);
	
	//获取一个有效的accesstoken
	public String getAccessToken(String appid);
	
	//查看是否有一个对应此appid的accesstoken
	public String getAccessTokenFlag(String appid);
	
	//通过appid修改更新accesstoken
	public int updateAccessTokenForAppid(String accessToken, String appid);
	
	//通过appid获取一个有效的JsApiTicket
	public String getJsApiTicketForData(String appid);
	
	//通过appid修改更新JsApiTicket
	public int updateJsApiTicketForAppid(String jsApiTicket, String appid);
	
	//通过appid获取一个config的Map
	public Map getWxConfigMap(String appid);
}
