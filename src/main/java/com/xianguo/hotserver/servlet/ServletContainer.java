package com.xianguo.hotserver.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;

/**
 * Servlet容器
 * @author Xianguo
 */
public class ServletContainer {
	
	private static Map<String,Servlet> servlets = new HashMap<>();
	
	public static void loadServlet(String path,Servlet servlet) {
		servlets.put(path, servlet);
	}
	
	public static Servlet getServlet(String path) {
		return servlets.get(path);
	}
	
}
