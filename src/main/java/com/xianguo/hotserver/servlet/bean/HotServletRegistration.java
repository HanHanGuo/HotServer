package com.xianguo.hotserver.servlet.bean;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRegistration;

public class HotServletRegistration implements ServletRegistration {
	
	private String name;
	
	private String className;
	
	private Map<String, String> initParameters;
	
	private Set<String> mappings;
	
	protected String runAsRole;
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getClassName() {
		return this.className;
	}

	@Override
	public boolean setInitParameter(String name, String value) {
		this.initParameters.put(name, value);
		return true;
	}

	@Override
	public String getInitParameter(String name) {
		return this.initParameters.get(name);
	}

	@Override
	public Set<String> setInitParameters(Map<String, String> initParameters) {
		this.initParameters.putAll(initParameters);
		return this.initParameters.keySet();
	}

	@Override
	public Map<String, String> getInitParameters() {
		return this.initParameters;
	}

	@Override
	public Set<String> addMapping(String... urlPatterns) {
		mappings.addAll(Arrays.asList(urlPatterns));
		return mappings;
	}

	@Override
	public Collection<String> getMappings() {
		return mappings;
	}

	@Override
	public String getRunAsRole() {
		return this.runAsRole;
	}

}
