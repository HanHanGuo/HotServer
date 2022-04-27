package com.xianguo.hotserver.servlet.impl;

import cn.hutool.core.collection.IteratorEnumeration;
import com.xianguo.hotserver.bean.RequestInfo;
import com.xianguo.hotserver.constants.HttpConstant;
import com.xianguo.hotserver.constants.ServerConstant;
import lombok.Setter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

/**
 * 实现了HttpServletRequest接口的request
 * @author Xianguo
 */
public class HotServletRequest extends RequestInfo implements HttpServletRequest {
	
	/**
	 * 编码
	 */
	private String characterEncoding = ServerConstant.ENCODE;

	@Setter
	private String contextPath = "";

	private Map<String,Object> attribute = new HashMap<>();
	
	@Override
	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return this.attribute.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return new IteratorEnumeration<>(attribute.keySet().iterator());
	}

	@Override
	public String getCharacterEncoding() {
		return characterEncoding;
	}

	@Override
	public void setCharacterEncoding(String characterEncoding) throws UnsupportedEncodingException {
		this.characterEncoding = characterEncoding;
	}

	@Override
	public int getContentLength() {
		try {
			return Integer.valueOf(super.getRequestHead().get(HttpConstant.HttpHeadName.CONTENT_LENGTH));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	@Override
	public long getContentLengthLong() {
		try {
			return Long.valueOf(super.getRequestHead().get(HttpConstant.HttpHeadName.CONTENT_LENGTH));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	@Override
	public String getContentType() {
		return super.getRequestHead().get(HttpConstant.HttpHeadName.CONTENT_TYPE);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String name) {
		List<String> values = super.getParameters().get(name);
		return values == null ? null : getEncodeString(values.get(0));
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return null;
	}

	@Override
	public String[] getParameterValues(String name) {
		List<String> values = super.getParameters().get(name);
		if(values != null) {
			String[] encodeValues = new String[values.size()];
			int index = 0;
			for (String value : values) {
				encodeValues[index] = getEncodeString(value);
				index++;
			}
			return encodeValues;
		}
		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> parMap = new HashMap<>();
		Map<String, List<String>> superParMap = super.getParameters();
		for(String key : superParMap.keySet()) {
			List<String> values = superParMap.get(key);
			String[] encodeValues = new String[values.size()];
			int index = 0;
			for(String value : values) {
				encodeValues[index] = getEncodeString(value);
				index++;
			}
			parMap.put(key, encodeValues);
		}
		return parMap;
	}

	@Override
	public String getProtocol() {
		return super.getRequestBaseInfo().getHttpType().getType() + "/" + super.getRequestBaseInfo().getHttpVersion();
	}

	@Override
	public String getScheme() {
		return super.getRequestBaseInfo().getHttpType().getType();
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return super.getIpAddress();
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(String name, Object o) {
		// TODO Auto-generated method stub
		this.attribute.put(name, o);
	}

	@Override
	public void removeAttribute(String name) {
		// TODO Auto-generated method stub
		this.attribute.remove(name);
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<Locale> getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
			throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DispatcherType getDispatcherType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDateHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHeader(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntHeader(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMethod() {
		return super.getRequestBaseInfo().getRequestMethod().getName();
	}

	@Override
	public String getPathInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return this.contextPath;
	}

	@Override
	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUserInRole(String role) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return super.getRequestBaseInfo().getUrl();
	}

	@Override
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession(boolean create) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changeSessionId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void login(String username, String password) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logout() throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 编码参数
	 * @author Xianguo
	 * @param parameter 参数
	 * @return 编码后的参数
	 */
	private String getEncodeString(String parameter) {
		try {
			return new String(parameter.getBytes(), characterEncoding);
		} catch (UnsupportedEncodingException e) {
			try {
				return new String(parameter.getBytes(), ServerConstant.ENCODE);
			} catch (UnsupportedEncodingException e1) {
				return parameter;
			}
		}
	}
}
