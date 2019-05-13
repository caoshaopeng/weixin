package com.njry.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;

import com.njry.util.RequestHelper;

import net.sf.json.JSONObject;

public class BaseController {
	
	public final String PATH = "weixin/";
	
	RequestHelper req = new RequestHelper();
	
	public void ajaxprint(HttpServletResponse response, int flag, String remark, Object object) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			Map map = new HashedMap();
			map.put("flag", flag);
			map.put("remark", remark);
			map.put("others", object);
			printWriter.write(JSONObject.fromObject(map).toString());
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
