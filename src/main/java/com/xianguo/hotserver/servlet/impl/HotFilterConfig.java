package com.xianguo.hotserver.servlet.impl;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Set;
import java.util.Vector;

public class HotFilterConfig implements FilterConfig {

    private ServletContext servletContext;

    private String name;

    public HotFilterConfig(String name, ServletContext servletContext){
        this.name = name;
        this.servletContext = servletContext;
    }

    @Override
    public String getFilterName() {
        return this.name;
    }

    @Override
    public ServletContext getServletContext() {
        return this.servletContext;
    }

    @Override
    public String getInitParameter(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return new Vector<String>().elements();
    }
}
