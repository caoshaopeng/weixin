package com.njry.model;

import lombok.Data;

@Data
public class AccessToken {
	
	private String access_token;//获取到的凭证
	
	private int expires_in;//凭证有效时间
	
	private String errcode;//返回码
	
	private String errmsg;//返回说明

}