package com.njry.model.wxconfig;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
public class Config {
	private String appId;
	
	private long timestamp;
	
	private String nonceStr;
	
	private String signature;
}
