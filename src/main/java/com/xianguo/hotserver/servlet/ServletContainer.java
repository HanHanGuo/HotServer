package com.xianguo.hotserver.servlet;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet容器
 * @author Xianguo
 */
public class ServletContainer {
	
	private static Map<String,Servlet> servlets = new HashMap<>();
	
//	@Getter
//	private static ServletContext servletContext = new HotServletContext();
	
	public static void loadServlet(String path,Servlet servlet) {
		servlets.put(path, servlet);
	}
	
	/*public static Servlet getServlet(String path) {
		return servlets.get(path);
	}*/
	
	public static void loadServlet() {
//		servlets = ClassUtil.finServletByFilePath("/test");
//		servlets.forEach((k,v)->{
//			ServletConfig con = new HotServletConfig(null,null,null);
//			System.out.println("LoadServlet:"+k+"\t"+v.getClass().getName());
//			try {
//				v.init(con);
//			} catch (ServletException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
	}

	
}
