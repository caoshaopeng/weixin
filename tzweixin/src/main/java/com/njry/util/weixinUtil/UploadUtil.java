package com.njry.util.weixinUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.springframework.stereotype.Component;

import com.njry.model.AccessToken;

import net.sf.json.JSONObject;

public class UploadUtil {
	private static final String UPLOAD_URL ="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	/**
	 * 文件上传
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
 
		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
 
		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 
 
		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
 
		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
 
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
 
		byte[] head = sb.toString().getBytes("utf-8");
 
		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);
 
		//文件正文部分
		//把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
 
		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线
 
		out.write(foot);
 
		out.flush();
		out.close();
 
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
 
		JSONObject jsonObj = JSONObject.fromObject(result);
		String typeName = "media_id";
		if("thumb".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	public static void main(String[] args) {
		WeiXinUtil weiXinUtil = new WeiXinUtil();
		String access_token = weiXinUtil.getAccess_Token();
		String path = "E:/12.png";
		try {
			String mediaId = upload(path, access_token, "image");
			System.out.println(mediaId+"===");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
