package com.xianguo.hotserver.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表单对象
 * @author Xianguo
 */
@Data
public class FormData {
	/**
	 * 表单数据
	 */
	private Map<String, List<Map<String, String>>> parameter = new HashMap<>();
	/**
	 * 分割字符串
	 */
	private String boundary;
	
}
