package com.xianguo.hotserver.bean;

import com.xianguo.hotserver.bean.enums.HttpMethod;
import com.xianguo.hotserver.bean.enums.HttpType;
import lombok.Data;

/**
 * http请求行基本数据
 * @author Xianguo
 */
@Data
public class RequestBaseInfo {
	/**
	 * 请求方法
	 */
	private HttpMethod requestMethod;
	
	/**
	 * 请求地址
	 */
	private String url;
	
	/**
	 * http类型
	 */
	private HttpType httpType;
	
	/**
	 * http版本
	 */
	private String httpVersion;
}
