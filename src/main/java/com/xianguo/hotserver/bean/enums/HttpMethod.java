package com.xianguo.hotserver.bean.enums;

/**
 * 请求方法枚举
 * 
 * @author Xianguo
 */
public enum HttpMethod {
	GET("GET"), POST("POST"), HEAD("HEAD"), PUT("PUT"), DELETE("DELETE"), CONNECT("CONNECT"), OPTIONS("OPTIONS"), TRACE("TRACE");
	
	private String name;
	
	HttpMethod(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
