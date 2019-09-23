package com.xianguo.hotserver.util;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebServlet;

import com.xianguo.hotserver.constants.ServerConstant;

/**
 * 类扫描工具
 * @author Xianguo
 *
 */
public class ClassUtil {
	
	public static Map<String,Servlet> finServletByFilePath(String content) {
		try {
			return finServletByFilePath(ServerConstant.PATH,content);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Map<String,Servlet> finServletByFilePath(String packagePath,String content) throws Exception{
		Map<String,Servlet> map = new HashMap<>();
		List<Class<?>> classes = findAndAddClassesInPackageByFile(packagePath);
		for(Class<?> item : classes) {
			Object obj = item.newInstance();
			if(!(obj instanceof Servlet)) {
				continue;
			}
			WebServlet webServlet = item.getAnnotation(WebServlet.class);
			if(webServlet != null) {
				String path = content+webServlet.value()[0];
				Servlet servlet = (Servlet)obj;
				map.put(path, servlet);
			}
		}
		return map;
	}
	
	/**
	 * 以文件的形式来获取包下的所有Class
	 * @param packagePath
	 * @return
	 */
	public static List<Class<?>> findClassByAnnotationAndFilePath(String packagePath){
		List<Class<?>> classes = new ArrayList<>();
		return classes;
	}
	
	/**
	 * 以文件的形式来获取包下的所有Class
	 * @param packagePath
	 * @return
	 */
	public static List<Class<?>> findAndAddClassesInPackageByFile(String packagePath) {
		return findAndAddClassesInPackageByFile(packagePath,true);
	}
	
	/**
	 * 以文件的形式来获取包下的所有Class
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @return
	 */
	public static List<Class<?>> findAndAddClassesInPackageByFile(String packagePath,final boolean recursive) {
		try {
			List<Class<?>> classes = new ArrayList<>();
			findAndAddClassesInPackageByFile(packagePath,recursive,classes);
			return classes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packagePath,final boolean recursive, List<Class<?>> classes) throws Exception {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".jar"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packagePath + "\\" + file.getName() + "\\", recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					URL[] urls = new URL[]{new URL("file",null,file.getPath())};
					URLClassLoader cl=new URLClassLoader(urls);
					classes.add(cl.loadClass("com.xianguo.servlt.xianguo"));
					// 添加到集合中去
					//classes.add(Class.forName(className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
