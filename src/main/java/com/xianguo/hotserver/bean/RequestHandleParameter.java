package com.xianguo.hotserver.bean;

import lombok.Data;

import java.io.InputStream;

/**
 * 请求处理参数
 * @author Xianguo
 */
@Data
public class RequestHandleParameter {
	
	/**
	 * 请求行和请求头String值
	 */
	private StringBuilder head;
	
	/**
	 * 正文下标
	 */
	private Integer contentIndex;
	
	/**
	 * 请求流
	 */
	private InputStream inputStream;
	
}
