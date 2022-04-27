package com.xianguo.hotserver.constants;

import java.io.File;

/**
 * 返回消息常量
 * @author Xianguo
 */
public class HttpConstant {
	/**
	 * 开发者名称
	 */
	public final static String DEVELOPER = "XianGuo";
	/**
	 * 请求url参数分隔符
	 */
	public final static String URL_SPLIT_SYMBOL = "?";
	/**
	 * 请求url参数分割符号
	 */
	public final static String PARAMETER_SPLIT_SYMBOL = "&";
	/**
	 * 等号
	 */
	public final static String EQUAL_SYMBOL = "=";

	/**
	 * 地址分隔符
	 */
	public final static String URL_SEPARATOR = "/";
	
	/**
	 * 返回头名称
	 * @author Xianguo
	 */
	public interface HttpHeadName{
		/**
		 * 内容类型
		 */
		public final static String CONTENT_TYPE = "content-type";
		/**
		 * 日期
		 */
		public final static String DATE = "date";
		/**
		 * 服务器
		 */
		public final static String SERVER = "server";
		/**
		 * 开发者
		 */
		public final static String DEVELOPER = "developer";
		/**
		 * 内容长度
		 */
		public final static String CONTENT_LENGTH = "content-length";
	}
}
