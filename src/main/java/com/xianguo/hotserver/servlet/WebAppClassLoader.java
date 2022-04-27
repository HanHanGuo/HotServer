package com.xianguo.hotserver.servlet;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import com.sun.tools.classfile.ClassFile;
import com.sun.tools.classfile.ConstantPoolException;
import com.xianguo.hotserver.constants.ExceptionConstant;
import com.xianguo.hotserver.constants.ServerConstant;
import com.xianguo.hotserver.servlet.aux.FileClass;
import com.xianguo.hotserver.servlet.impl.HotServletContext;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

@Slf4j
public class WebAppClassLoader extends ClassLoader{

    private String path;

    private String contentPath;

    @Getter
    private List<JarFile> jarFiles;

    private List<FileClass> classFiles;

    private Set<Class<?>> classList = new HashSet<>();

    private static FileFilter classFilter = (File file) -> file.getName().endsWith(".class");

    private static FileFilter jarFilter = (File file) -> file.getName().endsWith(".jar");

    static String webInfoClass = "WEB-INF"+File.separator+"classes";

    static String webInfoLib = "WEB-INF"+File.separator+"lib";



    public WebAppClassLoader(String contentPath){
        this.path = ServerConstant.WEB_CONTENT_PATH + File.separator + contentPath;
        this.contentPath = contentPath;
        this.jarFiles = new ArrayList<>();
        classFiles = FileUtil.loopFiles(this.path, classFilter).stream().map((f) -> new FileClass(f)).collect(Collectors.toList());

        jarFiles = FileUtil.loopFiles(this.path + File.separator + webInfoLib, jarFilter).stream().map((f)-> {
            try {
                return new JarFile(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    public WebAppClassLoader(String path, String contentPath, ClassLoader parent){
        super(parent);
        this.path = ServerConstant.WEB_CONTENT_PATH + File.separator + contentPath;
        this.contentPath = contentPath;
        this.jarFiles = new ArrayList<>();
        classFiles = FileUtil.loopFiles(ServerConstant.WEB_CONTENT_PATH + File.separator + contentPath, classFilter).stream().map(f->new FileClass(f)).collect(Collectors.toList());

        jarFiles = FileUtil.loopFiles(ServerConstant.WEB_CONTENT_PATH + File.separator + contentPath + File.separator + webInfoLib, jarFilter).stream().map((f)-> {
            try {
                return new JarFile(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    public void loadServlet(HotServletContext servletContext) {
        for (FileClass classFile : classFiles) {
            try {
                String className = classFile.getClassFile().getName().replaceAll("/", ".");
                Class<?> clazz = findClass(className, classFile.getAbsolutePath());
                if (clazz == null) {
                    log.error("class:" + className + "加载失败");
                    continue;
                }
                Class<?> superclass = clazz.getSuperclass();
                if (superclass != null && superclass.equals(HttpServlet.class)) {
                    WebServlet webServlet = clazz.getAnnotation(WebServlet.class);
                    if (webServlet != null) {
                        log.info("加载servlet:" + clazz);
                        servletContext.addServlet(webServlet.value(), (HttpServlet) clazz.newInstance());
                    }
                }

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ConstantPoolException e) {
                e.printStackTrace();
            }
        }
    }

    public Class<?> findClassByPath(String filePath)throws ClassNotFoundException {
        String className = filePath.replaceAll(path, "").replaceAll(File.separator, ".");
        className = className.substring(1,className.length()-6);
        return findClass(className,filePath);
    }

    public Class<?> findClass(String name,String classPath) throws ClassNotFoundException{
        try {
//            log.info("className:"+name);
            //读取字节码
            FileInputStream is = new FileInputStream(classPath);
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int index = -1;
            byte[] temp = new byte[1024];
            while ((index = is.read(temp)) != -1) {
                bo.write(temp, 0, index);
            }
            byte[] classByteArray = bo.toByteArray();
            definePackageInternal(name);
            Class<?> classs = super.defineClass(name, classByteArray, 0, classByteArray.length);
            classList.add(classs);
            return classs;
        } catch (FileNotFoundException e) {
            //尝试在jar包中加载
            return findClassByJar(name);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        } catch (LinkageError e){
            return loadClass(name);
        }
        throw new ClassNotFoundException(String.format(ExceptionConstant.CLASS_NOT_FOUND_EXCEPTION,name,classPath));
    }

    private void definePackageInternal(String name){
        int index = name.lastIndexOf(".");
        if(index != -1){
            String packageName = name.substring(0,index);
            if(getPackage(packageName) == null){
                definePackage(packageName, null, null, null, null, null, null, null);
            }
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//        log.info("clasName:"+name);
        return super.loadClass(name, resolve);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = this.path + File.separator + name.replaceAll("\\.", File.separator) + ".class";
        return findClass(name, classPath);
    }

    public Class<?> findClassByName(String name) throws ClassNotFoundException {
        return this.findClass(name);
    }

    public Set<Class<?>> findClassByType(Class<?> target){
        Set<Class<?>> classSet = new HashSet<>();
        try {
            //先找classFile
            for (Class classes : this.classList) {
                if(ClassUtil.isAllAssignableFrom(new Class[]{target}, new Class[]{classes})){
                    classSet.add(classes);
                }
            }
            //在找Jar包
            for (JarFile jarFile : this.jarFiles) {
                Enumeration<JarEntry> entryEnumeration = jarFile.entries();
                while (entryEnumeration.hasMoreElements()) {
                    // 获取JarEntry对象
                    JarEntry entry = entryEnumeration.nextElement();
                    if(entry.getName().endsWith(".class")){
                        Class classes = findClass(entry.getName().replaceAll("/", "."));
                        if(classes == null){
                            continue;
                        }
                        if(ClassUtil.isAllAssignableFrom(new Class[]{target}, new Class[]{classes})){
                            classSet.add(classes);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classSet;
    }

    public String classNameToJarEntry(String name){
        String s = name.replaceAll("\\.", "\\/");
        StringBuilder stringBuilder = new StringBuilder(s);
        stringBuilder.append(".class");
        return stringBuilder.toString();
    }

    protected Class<?> findClassByJar(String name) throws ClassNotFoundException {
        try {
            Class c = null;
            for (JarFile jarFile : this.jarFiles) {
                if (null != jarFile) {
                    String jarEntryName = classNameToJarEntry(name);
                    JarEntry entry = jarFile.getJarEntry(jarEntryName);
                    if(entry == null){
                        continue;
                    }
                    InputStream is = jarFile.getInputStream(entry);
                    int availableLen = is.available();
                    int len = 0;
                    byte[] bt1 = new byte[availableLen];
                    while (len < availableLen) {
                        len += is.read(bt1, len, availableLen - len);
                    }
                    is.close();
                    definePackageInternal(name);
                    return defineClass(name, bt1, 0, bt1.length);
                }
            }
            return null;
        } catch (IOException e) {
            throw new ClassNotFoundException("Class " + name + " not found.");
        }
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        try {
//            log.info("classLoder加载资源:" + name);
            Vector<URL> urlVector = new Vector<>();
            //查找class
            String path = ServerConstant.WEB_CONTENT_PATH + File.separator + this.contentPath + File.separator + webInfoClass+File.separator+name;
            if(new File(path).exists()){
                urlVector.add(new URL("file:"+path));
            }

            //查找jar
            for (JarFile jarFile : this.jarFiles) {
                JarEntry jarEntry = jarFile.getJarEntry(name);
                if (jarEntry != null) {
                    String url = "jar:file:"+jarFile.getName()+"!/"+name;
                    urlVector.add(new URL(url));
                }
            }
            return urlVector.elements();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按照路径获取资源Stream
     * @param name
     * @return
     */
    @Override
    public InputStream getResourceAsStream(String name) {
        try {
//            log.info("classLoder加载文件:" + name);
            //查找class
            String className = name.substring(0,name.length() - 6);
            for (FileClass classFile : this.classFiles) {
                if(classFile.getClassFile().getName().equals(className)){
                    return new FileInputStream(classFile);
                }
            }
            //查找jar
            for (JarFile jarFile : this.jarFiles) {
                JarEntry jarEntry = jarFile.getJarEntry(name);
                if (jarEntry != null) {
                    return jarFile.getInputStream(jarEntry);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ConstantPoolException e) {
            e.printStackTrace();
        }
        return null;
    }
}
