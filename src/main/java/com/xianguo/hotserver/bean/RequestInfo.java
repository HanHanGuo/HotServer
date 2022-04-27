package com.xianguo.hotserver.bean;

import com.xianguo.hotserver.bean.enums.ContentType;
import lombok.Data;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求信息
 * @author Xianguo
 */
@Data
public class RequestInfo {
	
	/**
	 * 请求基本信息
	 */
	private RequestBaseInfo requestBaseInfo;
	
	/**
	 * 请求头
	 */
	private Map<String, String> requestHead;
	
	/**
	 * 参数集合
	 */
	private Map<String, List<String>> Parameters = new HashMap<>();
	
	/**
	 * 正文开始下标
	 */
	private int contentIndex;
	
	/**
	 * 表单数据
	 */
	private FormData formData;
	
	/**
	 * 请求流
	 */
	private InputStream reqestStream;
	
	/**
	 * 内容类型
	 */
	private ContentType requestContentType;
	
	/**
	 * 访问者ip
	 */
	private String ipAddress;
}
