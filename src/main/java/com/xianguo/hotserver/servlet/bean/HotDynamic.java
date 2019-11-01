package com.xianguo.hotserver.servlet.bean;

import java.util.Set;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;

public class HotDynamic extends HotServletRegistration implements ServletRegistration.Dynamic {
	
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
		return null;
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
