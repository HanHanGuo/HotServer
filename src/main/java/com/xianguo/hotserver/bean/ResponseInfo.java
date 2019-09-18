package com.xianguo.hotserver.bean;

import java.io.FileInputStream;
import java.util.Map;

import lombok.Data;

/**
 * Http返回信息
 * 
 * @author Xianguo
 */
@Data
public class ResponseInfo {
	/**
	 * 消息基础信息
	 */
	private ResponseBaseInfo responseBaseInfo;

	/**
	 * 消息头
	 */
	private Map<String, String> responseHead;
	
	/**
	 * 返回内容
	 */
	private FileInputStream content;

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
