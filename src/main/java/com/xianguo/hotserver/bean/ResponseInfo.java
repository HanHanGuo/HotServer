package com.xianguo.hotserver.bean;

import com.xianguo.hotserver.socket.response.HotResponseOutputStream;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Http返回信息
 * 
 * @author Xianguo
 */
public class ResponseInfo {
	/**
	 * 消息基础信息
	 */
	@Getter
	@Setter
	private ResponseBaseInfo responseBaseInfo;

	/**
	 * 消息头
	 */
	@Getter
	private Map<String, String> responseHead = new HashMap<>();

	/**
	 * 返回内容文件
	 */
	@Getter
	@Setter
	private FileInputStream content;

	/**
	 * 返回内容buffer
	 */
	@Getter
	@Setter
	private HotResponseOutputStream hotResponseOutputStream;

	public void setResponseHead(Map<String,String> responseHead){
		this.responseHead.putAll(responseHead);
	}

	public String getResponseString() {
		StringBuilder sb = new StringBuilder();
		sb.append(responseBaseInfo.getHttpType().getType()).append("/")
				.append(responseBaseInfo.getVersion())
				.append(" ")
				.append(responseBaseInfo.getStatus().getCode())
				.append(" ")
				.append(responseBaseInfo.getStatus().getMsg())
				.append("\r\n");
		for(String key : responseHead.keySet()) {
			sb.append(key).append(": ").append(responseHead.get(key)).append("\r\n");
		}
		sb.append("\r\n");
		return sb.toString();
	}
}
