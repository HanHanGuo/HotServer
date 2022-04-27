package com.xianguo.hotserver.bean.enums;

/**
 * Http状态码枚举
 * @author Xianguo
 */
public enum HttpStatusCode {
	CODE_200(200,"OK"),CODE_404(404,"Not Found"),CODE_500(500,"Server Error");
	
	private Integer code;
	private String msg;
	HttpStatusCode(Integer code,String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public Integer getCode() {
		return this.code;
	}
	public String getMsg() {
		return this.msg;
	}
}
