package com.xianguo.hotserver.servlet.impl;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;
import java.util.HashSet;
import java.util.Set;

public class HotServletDynamic extends HotServletRegistration implements ServletRegistration.Dynamic {
	
	private boolean isAsyncSupported;
	
	private int loadOnStartup;
	
	private MultipartConfigElement multipartConfig;
	
	@Override
	public void setAsyncSupported(boolean isAsyncSupported) {
		this.isAsyncSupported = isAsyncSupported;
	}

	@Override
	public void setLoadOnStartup(int loadOnStartup) {
		this.loadOnStartup = loadOnStartup;
	}

	@Override
	public Set<String> setServletSecurity(ServletSecurityElement constraint) {
		// TODO Auto-generated method stub
		return new HashSet<>();
	}

	@Override
	public void setMultipartConfig(MultipartConfigElement multipartConfig) {
		this.multipartConfig = multipartConfig;
	}

	@Override
	public void setRunAsRole(String roleName) {
		super.runAsRole = roleName;
	}

}
