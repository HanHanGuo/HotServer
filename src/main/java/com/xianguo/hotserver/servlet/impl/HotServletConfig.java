package com.xianguo.hotserver.servlet.impl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * servlet配置，包括名称和参数
 * @author Xianguo
 */
public class HotServletConfig implements ServletConfig {
	
	private Map<String, String> initParameter = new HashMap<>();
	
	private Vector<String> initParameterVector = new Vector<String>();

	private ServletContext servletContext;
	
	private String servletName = "";
	
	public HotServletConfig(Map<String, String> initParameter,Vector<String> initParameterVector,String servletName,ServletContext servletContext) {
		this.initParameter = initParameter == null ? this.initParameter : initParameter;
		this.initParameterVector = initParameterVector == null ? this.initParameterVector : initParameterVector;
		this.servletName = servletName;
		this.servletContext = servletContext;
	}
	
	@Override
	public String getServletName() {
		return servletName;
	}

	@Override
	public ServletContext getServletContext() {
		return this.servletContext;
	}

	@Override
	public String getInitParameter(String name) {
		return initParameter.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return initParameterVector.elements();
	}

}
