package com.njry.message.response;

import com.njry.message.Voice;

import lombok.Data;

@Data
public class VoiceMessage extends BaseMessage {
	private Voice Voice;
}
