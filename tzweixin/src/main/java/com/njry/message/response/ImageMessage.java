package com.njry.message.response;

import com.njry.message.Image;

import lombok.Data;

@Data
public class ImageMessage extends BaseMessage {
	private Image Image;
	
}
