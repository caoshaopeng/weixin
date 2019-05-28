package com.njry.model.message.response;

import com.njry.model.message.Voice;

import lombok.Data;

@Data
public class VoiceMessage extends BaseMessage {
	private Voice Voice;
}
