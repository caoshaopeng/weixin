package com.njry.util.weixinUtil;

import java.util.Arrays;
/**
 * 微信接入验证
 * @author caoshaopeng
 *
 */
public class WxCheckHelper {
	private static final String TOKEN = "caoshaopeng";
	
	public static boolean checkSignature(String signature,String timestamp,String nonce) {
		String [] str = new String[] {TOKEN,timestamp,nonce};
		Arrays.sort(str);//排序
		StringBuffer buffer = new StringBuffer();//拼接字符串
		for(int i = 0; i < str.length; i++) {
			buffer.append(str[i]);
		}
		String temp = SHA1.encode(buffer.toString());//进行sha1加密
		return signature.equals(temp);//与微信提供的signature进行比对
	}
}
