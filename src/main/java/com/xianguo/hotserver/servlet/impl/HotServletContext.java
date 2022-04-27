package com.xianguo.hotserver.servlet.impl;

import com.xianguo.hotserver.bean.enums.FileType;
import com.xianguo.hotserver.constants.ServerConstant;
import com.xianguo.hotserver.servlet.WebAppClassLoader;
import com.xianguo.hotserver.servlet.entity.DynamicFilter;
import com.xianguo.hotserver.servlet.entity.DynamicServlet;
import com.xianguo.hotserver.servlet.entity.FilterMap;
import com.xianguo.hotserver.servlet.entity.ServletMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

/**
 * Servlet容器所有servlet存在这里
 * @author Xianguo
 */
@Slf4j
public class HotServletContext implements ServletContext {
	
	//servlet Map集合
	@Getter
	private ServletMap servletMap;
	//项目名称
	private String contextPath;
	//属性集合
	private Map<String, Object> attribute;
	//ServletContext名称
	private String name;
	//filterMap
	@Getter
	private FilterMap filterMap;
	/**
	 * web容器的ClassLoder
	 */
	private WebAppClassLoader classLoader;
	
	public HotServletContext(String contextPath, WebAppClassLoader webAppClassLoader) {
		this.servletMap = new ServletMap();
		this.attribute = new HashMap<>();
		this.classLoader = webAppClassLoader;
		this.filterMap = new FilterMap();
		this.contextPath = contextPath;
	}
	
	@Override
	public String getContextPath() {
		return this.contextPath;
	}

	@Override
	public ServletContext getContext(String uripath) {
		return this;
	}

	@Override
	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEffectiveMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEffectiveMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMimeType(String file) {
		FileType fileType = FileType.valueOf(file.substring(0,file.lastIndexOf(".")));
		return fileType == null ? FileType.TYPE_ALL.getContentType() : fileType.getContentType();
	}

	@Override
	public Set<String> getResourcePaths(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getResource(String path) throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getResourceAsStream(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDispatcher getNamedDispatcher(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Servlet getServlet(String name) throws ServletException {
		return this.servletMap.get(name).getServlet();
	}

	@Override
	public Enumeration<Servlet> getServlets() {
//		return new IteratorToEnumeration<Servlet>(servlets.entrySet().iterator());
		return null;
	}

	@Override
	public Enumeration<String> getServletNames() {
//		return new IteratorToEnumeration<String>(servlets.keySet().iterator());
		return null;
	}

	@Override
	public void log(String msg) {
		// TODO Auto-generated method stub
		log.info(msg);
	}

	@Override
	public void log(Exception exception, String msg) {
		// TODO Auto-generated method stub
		log.error(msg,exception);
	}

	@Override
	public void log(String message, Throwable throwable) {
		// TODO Auto-generated method stub
		log.error(message,throwable);
	}

	@Override
	public String getRealPath(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInitParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		// TODO Auto-generated method stub
		return new Vector<String>().elements();
	}

	@Override
	public boolean setInitParameter(String name, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getAttribute(String name) {
		return attribute.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return new IteratorToEnumeration<String>(this.attribute.keySet().iterator());
	}

	@Override
	public void setAttribute(String name, Object object) {
		this.attribute.put(name, object);
	}

	@Override
	public void removeAttribute(String name) {
		this.attribute.remove(name);
	}

	@Override
	public String getServletContextName() {
		return this.name;
	}

	@Override
	public Dynamic addServlet(String servletName, String className) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addServlet(String[] servletName, HttpServlet httpServlet){
//		this.servletMap.putServlet(servletName, httpServlet);
	}

	@Override
	public Dynamic addServlet(String servletName, Servlet servlet) {
		try {
			DynamicServlet dynamicServlet = new DynamicServlet();
			HotServletConfig servletConfig = new HotServletConfig(null, null, servletName, this);
			servlet.init(servletConfig);
			dynamicServlet.setServlet(servlet);
			this.servletMap.putServlet(servletName, dynamicServlet);
			return dynamicServlet.getDynamic();
		}catch (ServletException e){
			log.error(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dynamic addJspFile(String servletName, String jspFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletRegistration getServletRegistration(String servletName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) {
		// TODO Auto-generated method stub
		try {
			return addFilter(filterName, (Filter) this.classLoader.findClassByName(className).newInstance());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
		// TODO Auto-generated method stub
		try {
			DynamicFilter dynamicFilter = new DynamicFilter(filter);
			FilterConfig filterConfig = new HotFilterConfig(filterName, this);
			filter.init(filterConfig);
			filterMap.put(filterName, dynamicFilter);
			return dynamicFilter.getDynamic();
		} catch (ServletException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}

	@Override
	public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
		// TODO Auto-generated method stub
		try {
			return addFilter(filterName, filterClass.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilterRegistration getFilterRegistration(String filterName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addListener(String className) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends EventListener> void addListener(T t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addListener(Class<? extends EventListener> listenerClass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JspConfigDescriptor getJspConfigDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		System.out.println("classloder!!!!!!!!!!!!!!!!!!!!");
		return this.classLoader;
	}

	@Override
	public void declareRoles(String... roleNames) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getVirtualServerName() {
		return ServerConstant.SERVER;
	}

	@Override
	public int getSessionTimeout() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setSessionTimeout(int sessionTimeout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRequestCharacterEncoding() {
		return ServerConstant.ENCODE;
	}

	@Override
	public void setRequestCharacterEncoding(String encoding) {
		ServerConstant.ENCODE = encoding;
	}

	@Override
	public String getResponseCharacterEncoding() {
		return ServerConstant.ENCODE;
	}

	@Override
	public void setResponseCharacterEncoding(String encoding) {
		ServerConstant.ENCODE = encoding;
	}
	
	public class IteratorToEnumeration<E> implements Enumeration<E> {
		
		private Iterator<?> iterator;
		
		public IteratorToEnumeration(Iterator<?> iterator) {
			this.iterator = iterator;
		}
		
		@Override
		public boolean hasMoreElements() {
			return iterator.hasNext();
		}

		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public E nextElement() {
			E value = (E) iterator.next();
			if(value instanceof Entry) {
				return (E) ((Entry) value).getValue();
			}
			return value;
		}
		
	}

}
