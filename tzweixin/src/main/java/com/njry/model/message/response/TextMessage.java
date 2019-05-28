package com.njry.model.message.response;
import lombok.Data;

@Data
public class TextMessage extends BaseMessage {
	private String Content;//文本消息内容
	
	private String MsgId;//消息id，64位整型
	
	public TextMessage(){
		
	}
 
	public TextMessage(String toUserName, String fromUserName,
			long createTime, String msgType, String content, String msgId) {
		super();
		ToUserName = toUserName;
		FromUserName = fromUserName;
		CreateTime = createTime;
		MsgType = msgType;
		Content = content;
		MsgId = msgId;
	}
}
