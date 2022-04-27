package com.xianguo.hotserver.servlet;

import com.xianguo.hotserver.constants.HttpConstant;
import com.xianguo.hotserver.servlet.impl.HotServletContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
public class WebAppLoader {

//    private static Map<String, WebApp> webAppMap = new HashMap<>();

    private static Map<String, HotServletContext> servletContextMap = new HashMap<>();


    //加载webApp
    public static void loadWebApp(String contentPath) {
        WebAppClassLoader classLoader = new WebAppClassLoader(contentPath);
        HotServletContext servletContext = new HotServletContext("/"+contentPath, classLoader);
        classLoader.loadServlet(servletContext);

        //加载SPI
        loadSPI(servletContext, classLoader);
        servletContextMap.put(contentPath, servletContext);
        log.info("WebApp:" + contentPath + "加载完成。");
    }

    /**
     * 加载servlet的SPI规范
     * @param servletContext
     */
    public static void loadSPI(HotServletContext servletContext, WebAppClassLoader webAppClassLoader){
        for (JarFile jarFile : webAppClassLoader.getJarFiles()) {
            try {
                JarEntry jarEntry = jarFile.getJarEntry("META-INF/services/javax.servlet.ServletContainerInitializer");
                if(jarEntry == null){
                    continue;
                }
                InputStream inputStream = jarFile.getInputStream(jarEntry);
                byte[] temp = new byte[1024];
                int index = -1;
                StringBuilder initializerContent = new StringBuilder();
                while ((index = inputStream.read(temp)) != -1) {
                    initializerContent.append(new String(temp, 0, index));
                }
                inputStream.close();
                Class<?> initClass = webAppClassLoader.findClass(initializerContent.toString());
                if(initClass != null){
                    //解析HandlesTypes
                    HandlesTypes handlesTypes = initClass.getAnnotation(HandlesTypes.class);
                    if(handlesTypes != null){
                        Class<?>[] handlesList = handlesTypes.value();

                        Object initObj = initClass.newInstance();
                        if(initObj instanceof ServletContainerInitializer){
                            ServletContainerInitializer servletContainerInitializer = (ServletContainerInitializer) initObj;
                            new Thread(()->{
                                try {
                                    Thread.currentThread().setContextClassLoader(webAppClassLoader);
                                    servletContainerInitializer.onStartup(webAppClassLoader.findClassByType(handlesList[0]), servletContext);
                                } catch (ServletException e) {
                                    e.printStackTrace();
                                }
                            }).run();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static Servlet getServlet(String url) throws ServletException {
        HotServletContext servletContext = getServletContext(url);
        if(servletContext != null){
            return servletContext.getServletMap().getServletByMapping(url);
        }
        return null;
    }

    public static List<Filter> getFilter(String url){
        HotServletContext servletContext = getServletContext(url);
        if(servletContext != null){
            return servletContext.getFilterMap().getFilter(url);
        }
        return null;
    }

    public static HotServletContext getServletContext(String url){
        String newUrl = url.substring(1);
        Integer index = newUrl.indexOf(HttpConstant.URL_SEPARATOR);
        if(index != -1){
            String contentPath = newUrl.substring(0, index);
            HotServletContext servletContext = servletContextMap.get(contentPath);
            return servletContext;
        }
        return null;
    }

//    public static String getMapping(String url){
//        String contextPath = getServletContext(url).getContextPath();
//        int index = contextPath.indexOf(contextPath);
//        if(index != -1){
//
//        }
//    }

}
