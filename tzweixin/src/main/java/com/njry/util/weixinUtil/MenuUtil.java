package com.njry.util.weixinUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.njry.model.button.Button;
import com.njry.model.button.ClickButton;
import com.njry.model.button.Menu;
import com.njry.model.button.ViewButton;
import com.njry.util.HttpRequestHelper;

import net.sf.json.JSONObject;

@Component
public class MenuUtil {
	private static final String CTRATE_MENU_URL  = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	private static final String DEL_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	@Autowired
	WeiXinUtil weiXinUtil;
	
	/**
	 * 创建菜单
	 * @param accessToken 
	 * @param Menu 菜单json格式字符串
	 * @return
	 */
	public static int createMenu(String accessToken,String Menu){
		int result = Integer.MIN_VALUE;
		String url = CTRATE_MENU_URL.replaceAll("ACCESS_TOKEN", accessToken);
		JSONObject json = HttpRequestHelper.doPoststr(url, Menu);
		if(json!=null){
			//从返回的数据包中取数据{"errcode":0,"errmsg":"ok"}
			result = json.getInt("errcode");
		}
		return result;
	}
	
	public static int delMenu(String access_token) {
		JSONObject json = HttpRequestHelper.doGetstr(DEL_MENU_URL.replaceAll("ACCESS_TOKEN", access_token));
		int result = -1;
		if(json != null) {
			result = json.getInt("errcode");
		}
		return result;
	}
	
	public static String initMenu(){
		String result = "";
		//创建点击一级菜单
		ClickButton button11 = new ClickButton();
		button11.setName("往期活动");
		button11.setKey("11");
		button11.setType("click");
		
		//创建跳转型一级菜单
		ViewButton button21 = new ViewButton();
		button21.setName("网页测试");
		button21.setType("view");
		button21.setUrl("http://caoshaopeng.natapp1.cc/Test/frame.do");
		
		//创建其他类型的菜单与click型用法一致
		ClickButton button31 = new ClickButton();
		button31.setName("拍照发图");
		button31.setType("pic_photo_or_album");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("发送位置");
		button32.setKey("32");
		button32.setType("location_select");
		
		//封装到一级菜单
		Button button = new Button();
		button.setName("菜单");
		button.setType("click");
		button.setSub_button(new Button[]{button31,button32});
		
		//封装菜单
		Menu menu = new Menu();
		menu.setButton(new Button[]{button11,button21,button});
		return JSONObject.fromObject(menu).toString();
	}
	
	public static void main(String[] args) {
		WeiXinUtil weiXinUtil = new WeiXinUtil();
		
		String accessToken  = weiXinUtil.getAccess_Token();
		String menu = MenuUtil.initMenu();
		System.out.println(menu);
		
		  int result = MenuUtil.createMenu(accessToken,menu);
		  
		  if(result==0){ System.out.println("菜单创建成功"); } else{
		  System.out.println("错误码"+result); }
		 
		
		/*
		 * int result = delMenu(accessToken); if(result == 0) {
		 * System.out.println("删除成功！"); } else { System.out.println(result); }
		 */
	}
}
