package org.xsmart.system.util;

import org.apache.log4j.Logger;
import org.xsmart.system.core.DatabaseManager;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

    private static Logger logger = Logger.getLogger(ClassUtil.class);
    private static HashSet<Class<?>> resultSet = new HashSet<>();

    public static Class<?> loadClass(String className){
        return loadClass(className,false);
    }

    public static Class<?> loadClass(String className, boolean initialize){
        Class<?> object = null;
        try {
            object = Class.forName(className,initialize,Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("ClassUtil loadClass error :" + e.getMessage());
            e.printStackTrace();
        }
        return object;
    }

    /**
     * 根据包名获取包下的类
     */
    public static HashSet<Class<?>> addPackageClass(String packageName){
        resultSet.clear();
        String packagePath = packageName.replace(".","/");
        try {
            Enumeration<URL> resource =  Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while (resource.hasMoreElements()){
                URL url = resource.nextElement();
                if(url == null){
                    continue;
                }

                String protocol = url.getProtocol();
                if (protocol.equals("file")) {
                    addClassFromFileAndDir(url.getPath(), packageName);
                }

                if (protocol.equals("jar")) {
                    JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                    if(jarURLConnection != null){
                        JarFile jarFile = jarURLConnection.getJarFile();
                        if(jarFile != null){
                            Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                            while (jarEntryEnumeration.hasMoreElements()){
                                JarEntry jarEntry = jarEntryEnumeration.nextElement();
                                String entryName = jarEntry.getName();
                                if(entryName.endsWith(".class")){
                                    String jarClassName = entryName.substring(0,entryName.lastIndexOf(".")).replaceAll("/",".");
                                    addClassToResultSet(jarClassName);
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * 递归从包的文件/文件夹中加载class
     */
    private static void addClassFromFileAndDir(String packagePath, String packageName){
        File[] fileList = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for(File file : fileList){
            if(file.isDirectory()){
                String subPackagePath = packagePath + "/" + file.getName();
                String subPackageName = packageName + "." + file.getName();
                addClassFromFileAndDir(subPackagePath,subPackageName);
            }

            if(file.isFile()){
                String classFileName = file.getName().substring(0,file.getName().lastIndexOf("."));
                addClassToResultSet(packageName + "." + classFileName);
            }
        }

    }

    /**
     *
     */
    private static void addClassToResultSet(String className){
        Class<?> claszz = loadClass(className,false);
        resultSet.add(claszz);
    }


}
