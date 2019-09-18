package com.xianguo.hotserver.bean.enums;

/**
 * http协议版本枚举
 * @author Xianguo
 */
public enum HttpType {
	HTTP("HTTP"),HTTPS("HTTPS");
	
	private String httpType;
	
	HttpType(String httpType) {
		this.httpType = httpType;
	}
	
	public String getType() {
		return this.httpType;
	}
}
