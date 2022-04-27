package com.xianguo.hotserver.servlet.entity;

import com.xianguo.hotserver.servlet.WebAppClassLoader;
import lombok.Data;

import java.util.HashMap;

@Data
public class WebApp {

    private String contentPath;

    private WebAppClassLoader webAppClassLoader;

    private ServletMap servletMap;

    public WebApp(String contentPath, WebAppClassLoader webAppClassLoader){
        this.servletMap = new ServletMap();
        this.contentPath = contentPath;
        this.webAppClassLoader = webAppClassLoader;
    }

    public static class ServletMap extends HashMap<String, DynamicServlet>{

        public void putServlet(String[] names, DynamicServlet dynamicServlet) {
            for (String url : names) {
                put(url, dynamicServlet);
            }
        }
        public void putServlet(String name, DynamicServlet dynamicServlet){
            put(name,dynamicServlet);
        }
    }
}
