package com.njry.util;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
	
	public String getValue(HttpServletRequest request, String param) {
		String obj =  request.getParameter(param);
		if(obj == null || obj.equals("")) {
			return "";
		}
		return obj.trim();
	}
	
	public String[] getValues(HttpServletRequest request, String param) {
		String [] obj = request.getParameterValues(param);
		if(obj == null) {
			obj = new String[0];
		}
		return obj;
	}
	
}
