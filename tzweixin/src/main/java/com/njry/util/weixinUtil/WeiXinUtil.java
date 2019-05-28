package com.njry.util.weixinUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.map.HashedMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.njry.model.AccessToken;
import com.njry.model.wxconfig.Config;
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
 * 先从redis缓存读取 如果没有值在取服务器里的值
 * 获取Config配置 用于js-sdk
 * @author caoshaopeng
 *
 */
@Slf4j
public class WeiXinUtil {
	/**
	 * 开发者id
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
	 * 从接口中获取access_token
	 * @return
	 */
	public static AccessToken getAccessToken(){
		//System.out.println("从接口中获取");
		Jedis jedis  = RedisUtil.getJedis();
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject json = HttpRequestHelper.doGetstr(url);
		if(json!=null && json.containsKey("access_token")){
			token.setAccess_token(json.getString("access_token"));
			token.setExpires_in(json.getInt("expires_in"));
			jedis.set("access_token", json.getString("access_token"));
			jedis.expire("access_token", 60*59*2);//60*60*2为两小时
		} else {
			token.setAccess_token("");
			token.setExpires_in(0);
			log.info("错误信息："+json.getString("errmsg")+"错误码："+json.getString("errcode"));
		}
		RedisUtil.returnResource(jedis);
		return token;
	}
	/**
	 * 获取凭证
	 * @return
	 */
	public static String getAccess_Token(){
		//System.out.println("从缓存中读取");
		Jedis jedis  = RedisUtil.getJedis();
		String access_token = jedis.get("access_token");
		if(access_token==null || access_token.isEmpty()){
			AccessToken token = getAccessToken();
			access_token = token.getAccess_token();
		}
		RedisUtil.returnResource(jedis);
		return access_token;
	}
	
	public static String getJSApiTicket() {
		Jedis jedis = RedisUtil.getJedis();
		String access_token = WeiXinUtil.getAccess_Token();
		String urlStr = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi";
		JSONObject j_ticket = HttpRequestHelper.doGetstr(urlStr);
		String ticket = "";
		if(j_ticket != null && j_ticket.containsKey("ticket")) {
			ticket = j_ticket.getString("ticket");
			jedis.set("ticket", ticket);
			jedis.expire("ticket", 60 * 59 * 2);
		}
		RedisUtil.returnResource(jedis);
		return ticket;
	}
	
	public static String getJSApi_Ticket() {
		Jedis jedis = RedisUtil.getJedis();
		String ticket = jedis.get("ticket");
		if(ticket == null || ticket.equals("")) {
			ticket = WeiXinUtil.getJSApiTicket();
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
		/*
		 * String access_token = WeiXinUtil.getAccess_Token();
		 * if(!access_token.equals("")) {
		 * System.out.println("调用成功access_token:"+access_token); }
		 */
		
		String signature = getSignature(getTimeStamp(), getNonceStr(), "http://w9u25e.natappfree.cc/Test/frame.do", getJSApi_Ticket());
		System.out.println(signature);
		
	}

}
