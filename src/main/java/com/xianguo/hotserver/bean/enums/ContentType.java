package com.xianguo.hotserver.bean.enums;

/**
 * 常见ContentType
 * @author Xianguo
 */
public enum ContentType {
	FORM_DATA_URLENCODED("application/x-www-form-urlencoded"),
	FORM_DATA("multipart/form-data"),
	JSON("application/json"),
	XML("text/xml");
	
	private String contentType;
	
	ContentType(String contentType){
		this.contentType = contentType;
	}
	
	public String getContentType() {
		return this.contentType;
	}
}
