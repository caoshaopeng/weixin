package com.njry.util.weixinUtil;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import com.njry.message.Image;
import com.njry.message.response.ImageMessage;
import com.thoughtworks.xstream.XStream;

public class ImageMessageUtil implements BaseMessageUtil<ImageMessage> {

	@Override
	public String messageToxml(ImageMessage imageMessage) {
		XStream xtream = new XStream();
		xtream.alias("xml", imageMessage.getClass());
		xtream.alias("Image", new Image().getClass());
		return xtream.toXML(imageMessage);
	}

	@Override
	public String initMessage(String FromUserName, String ToUserName) {
		Image Image = new Image();
		Image.setMediaId(getmediaId());
		ImageMessage message = new ImageMessage();
		message.setFromUserName(ToUserName);
		message.setToUserName(FromUserName);
		message.setCreateTime(new Date().getTime());
		message.setMsgType("image");
		message.setImage(Image);
		return messageToxml(message);
	}
	
	/**
	 * 获取Media
	 * @return
	 */
	public String getmediaId(){
		String path = "E:/12.png";
		String accessToken = WeiXinUtil.getAccess_Token();
		String mediaId = null;
		try {
			mediaId = UploadUtil.upload(path, accessToken, "image");
		} catch (KeyManagementException | NoSuchAlgorithmException
				| NoSuchProviderException | IOException e) {
			e.printStackTrace();
		}
		return mediaId;
	}

}
