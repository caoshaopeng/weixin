package com.njry.model.message.response;

import lombok.Data;

@Data
public class BaseMessage {
	protected String ToUserName;
	protected String FromUserName;
	protected long CreateTime;
	protected String MsgType;
 
	public BaseMessage() {
		super();
	}
}
