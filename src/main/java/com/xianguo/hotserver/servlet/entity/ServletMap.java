package com.xianguo.hotserver.servlet.entity;

import javax.servlet.Servlet;
import java.util.HashMap;

public class ServletMap extends HashMap<String, DynamicServlet> {

    public void putServlet(String[] names, DynamicServlet dynamicServlet) {
        for (String url : names) {
            put(url, dynamicServlet);
        }
    }
    
    public void putServlet(String name, DynamicServlet dynamicServlet){
        put(name,dynamicServlet);
    }
    
    public Servlet getServletByMapping(String mapping){
        for (Entry<String, DynamicServlet> dynamicServletEntry : this.entrySet()) {
            DynamicServlet dynamicServlet = dynamicServletEntry.getValue();
            for (String dynamicMapping : dynamicServlet.getDynamic().getMappings()) {
                if(mapping.startsWith(dynamicMapping)){
                    return dynamicServlet.getServlet();
                }
            }
        }
        return null;
    }
}