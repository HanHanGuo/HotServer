package com.xianguo.hotserver.servlet;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import com.xianguo.hotserver.servlet.bean.HotServletConfig;
import com.xianguo.hotserver.servlet.bean.HotServletContext;
import com.xianguo.hotserver.util.ClassUtil;

import lombok.Getter;

/**
 * Servlet容器
 * @author Xianguo
 */
public class ServletContainer {
	
	private static Map<String,Servlet> servlets = new HashMap<>();
	
	@Getter
	private static ServletContext servletContext = new HotServletContext();
	
	public static void loadServlet(String path,Servlet servlet) {
		servlets.put(path, servlet);
	}
	
	/*public static Servlet getServlet(String path) {
		return servlets.get(path);
	}*/
	
	public static void loadServlet() {
		servlets = ClassUtil.finServletByFilePath("/test");
		servlets.forEach((k,v)->{
			ServletConfig con = new HotServletConfig(null,null,null);
			System.out.println("LoadServlet:"+k+"\t"+v.getClass().getName());
			try {
				v.init(con);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	
}
