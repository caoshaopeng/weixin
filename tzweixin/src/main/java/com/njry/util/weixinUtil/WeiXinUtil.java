package com.njry.util.weixinUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.njry.model.AccessToken;
import com.njry.model.wxconfig.Config;
import com.njry.service.utilservice.WeiXinUtilService;
import com.njry.util.HttpRequestHelper;
import com.njry.util.RedisUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * 获取
 * AccessToken
 * jsapi_ticket
 * 先从redis缓存读取 然后再数据库读取 如果没有值在取服务器里的值
 * 获取Config配置 用于js-sdk
 * @author caoshaopeng
 *
 */
@Slf4j
@Component
public class WeiXinUtil {
	/**
	 * 开发者id
	 * 此为单个公众号项目
	 */
	@Getter
	private static final String APPID = "wxf4275a64ac5d819d";
	/**
	 * 开发者秘钥
	 */
	private static final String APPSECRET="515a5ce30f6766eb155772f572cb0c4d";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?"
			+ "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 有效时间
	 */
	private static final String EXPIRED_TIME = "7200";
	
	@Autowired
	WeiXinUtilService weiXinUtilService;
	
	
	/**
	 * 获取凭证
	 * @return
	 */
	public String getAccess_Token(){
		System.out.println("从缓存中读取");
		Jedis jedis  = RedisUtil.getJedis();
		String access_token = jedis.get("access_token");		
		System.out.println("从缓存中读取结束");
		if(access_token==null || access_token.isEmpty()){
			//从数据库中获取
			access_token = this.getAccessTokenForData();
		}
		RedisUtil.returnResource(jedis);
		return access_token;
	}
	
	/**
	 * 获取凭证从数据库
	 * @return
	 */
	public String getAccessTokenForData(){
		System.out.println("从数据库读取");
		String accessToken = weiXinUtilService.getAccessToken(APPID);
		if(accessToken == null || accessToken.equals("")) {
			//从公众号获取
			AccessToken token = getAccessToken();
			accessToken = token.getAccess_token();
		}
		return accessToken;
	}
	
	/**
	 * 从接口中获取access_token
	 * @return
	 */
	public AccessToken getAccessToken(){
		System.out.println("从接口中获取");
		Jedis jedis  = RedisUtil.getJedis();
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject json = HttpRequestHelper.doGetstr(url);
		if(json!=null && json.containsKey("access_token")){
			//先插入数据库 后放入缓存，以防出现问题数据库没有更新
			if(weiXinUtilService.insertAccessToken(json.getString("access_token"), EXPIRED_TIME, APPID) > 0) {
				token.setAccess_token(json.getString("access_token"));
				token.setExpires_in(json.getInt("expires_in"));
				jedis.set("access_token", json.getString("access_token"));
				jedis.expire("access_token", 60*59*2);//60*60*2为两小时
			}
		} else {
			token.setAccess_token("");
			token.setExpires_in(0);
			log.info("错误信息："+json.getString("errmsg")+"错误码："+json.getString("errcode"));
		}
		RedisUtil.returnResource(jedis);
		return token;
	}
	
	/**
	 * 从redis缓存中获取JSApiTicket，没有从数据库获取
	 * @return
	 */
	public String getJSApi_Ticket() {
		System.out.println("从缓存获取jsapiticket");
		Jedis jedis = RedisUtil.getJedis();
		String ticket = jedis.get("ticket");
		if(ticket == null || ticket.equals("")) {
			ticket = getJsApiTicketForData();
		}
		RedisUtil.returnResource(jedis);
		return ticket;
	}
	
	/**
	 * 从数据库获取JsApiTicket
	 * @return
	 */
	public String getJsApiTicketForData() {
		String JsApiTicket = weiXinUtilService.getJsApiTicketForData(APPID);
		if(JsApiTicket == null || JsApiTicket.equals("")) {
			JsApiTicket = this.getJSApiTicket();
		}
		return JsApiTicket;
	}
	
	/**
	 * 从公众号获取JSApiTicket 如果获取到放入redis缓存中，并记录数据库
	 * @return
	 */
	public String getJSApiTicket() {
		System.out.println("从公众号获取jsapiticket");
		Jedis jedis = RedisUtil.getJedis();
		String access_token = this.getAccess_Token();
		String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
		JSONObject j_ticket = HttpRequestHelper.doGetstr(urlStr);
		String ticket = "";
		if(j_ticket != null && j_ticket.containsKey("ticket")) {
			//跟上面同理
			if(weiXinUtilService.updateJsApiTicketForAppid(ticket, APPID) > 0) {
				ticket = j_ticket.getString("ticket");
				jedis.set("ticket", ticket);
				jedis.expire("ticket", 60 * 59 * 2);
			}
		}
		RedisUtil.returnResource(jedis);
		return ticket;
	}
	
	public static long getTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	public static String getNonceStr() {
		return UUID.randomUUID().toString();
	}
	
	public static String getSignature(long timestamp, String noncestr, String url, String jsapi_ticket) {
		StringBuffer signature = new StringBuffer();
		signature.append("jsapi_ticket=" + jsapi_ticket + "&");
		signature.append("noncestr=" + noncestr + "&");
		signature.append("timestamp=" + timestamp + "&");
		signature.append("url=" + url);
		String s_signature = SHA1.encode(signature.toString());
		return s_signature;
	}
	
	public static Map getConfigMap(Config config) {
		Map map = new HashMap();
		if(config != null) {
			map.put("appId", config.getAppId());			
			map.put("timestamp", config.getTimestamp());
			map.put("nonceStr", config.getNonceStr());
			map.put("signature", config.getSignature());
		}
		return map;
	}
	
	public static void main(String[] args) {
		WeiXinUtil weiXinUtil = new WeiXinUtil();
		//String signature = getSignature(getTimeStamp(), getNonceStr(), "http://w9u25e.natappfree.cc/Test/frame.do", weiXinUtil.getJSApi_Ticket());
		//System.out.println(signature);
		String access_token = weiXinUtil.getAccess_Token();
		if(!access_token.equals("")) {
			System.out.println("调用成功access_token:"+access_token); 
		}
	}

}
