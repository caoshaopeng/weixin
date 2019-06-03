package com.njry.service.impl.utilservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.njry.mapper.utilMapper.UtilMapper;
import com.njry.service.utilservice.WeiXinUtilService;

@Service("weiXinUtilService")
public class WeiXinUtilServiceImpl implements WeiXinUtilService {

	@Autowired
	UtilMapper utilMapper;
	
	@Override
	public int insertAccessToken(String accessToken, String time, String appid) {
		int flag = 0;
		String accessTokenflag = getAccessTokenFlag(appid);
		if(accessTokenflag == null || accessTokenflag.equals("")) {
			flag = utilMapper.insertAccessToken(accessToken, time, appid);
		} else {
			flag = this.updateAccessTokenForAppid(accessToken, appid);
		}	
		return flag;
	}

	@Override
	public String getAccessToken(String appid) {
		String accessToken = utilMapper.getAccessToken(appid);
		return accessToken;
	}

	@Override
	public String getAccessTokenFlag(String appid) {
		String accessToken = utilMapper.getAccessTokenFlag(appid);
		return accessToken;
	}

	@Override
	public int updateAccessTokenForAppid(String accesstoken, String appid) {
		int i = utilMapper.updateAccessTokenForAppid(accesstoken, appid);
		return i;
	}

	@Override
	public String getJsApiTicketForData(String appid) {
		String JsApiTicket = utilMapper.getJsApiTicketForData(appid);
		return JsApiTicket;
	}

	@Transactional
	@Override
	public int updateJsApiTicketForAppid(String JsApiTicket, String appid) {
		int i = utilMapper.updateJsApiTicketForAppid(JsApiTicket, appid) ;
		return i;
	}

}
