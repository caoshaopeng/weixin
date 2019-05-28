package com.njry.model.message.response;

import com.njry.model.message.Image;

import lombok.Data;

@Data
public class ImageMessage extends BaseMessage {
	private Image Image;
	
}
