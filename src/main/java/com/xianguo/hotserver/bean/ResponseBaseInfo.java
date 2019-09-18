package com.xianguo.hotserver.bean;

import com.xianguo.hotserver.bean.enums.HttpStatusCode;
import com.xianguo.hotserver.bean.enums.HttpType;

import lombok.Data;

/**
 * 消息基础信息
 * @author Xianguo
 */
@Data
public class ResponseBaseInfo {
	/**
	 * http类别
	 */
	private HttpType httpType;
	/**
	 * http版本
	 */
	private String version;
	/**
	 * 返回状态码
	 */
	private HttpStatusCode status;
}
