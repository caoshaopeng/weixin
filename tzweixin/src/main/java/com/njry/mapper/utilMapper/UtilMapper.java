package com.njry.mapper.utilMapper;

import java.util.List;
import io.lettuce.core.dynamic.annotation.Param;

public interface UtilMapper {

	public int insertAccessToken(@Param("accessToken") String accessToken, 
								 @Param("time") String time, @Param("appid") String appid);
	
	public String getAccessToken(@Param("appid") String appid);
	
	public String getAccessTokenFlag(@Param("appid") String appid);
	
	public int updateAccessTokenForAppid(@Param("accesstoken") String accesstoken, @Param("appid") String appid);
	
	public String getJsApiTicketForData(@Param("appid") String appid);
	
	public int updateJsApiTicketForAppid(@Param("JsApiTicket") String JsApiTicket, @Param("appid") String appid);
}
