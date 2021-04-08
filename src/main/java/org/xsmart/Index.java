package org.xsmart;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;

public class Index {

    public static void main(String[] args) {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.getInteger("port", 8080));
        tomcat.getConnector();
        // 创建 WebApp
        Context context = null;
        try {
            String a = new File("src/main/webapp").getAbsolutePath();
            context = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
            WebResourceRoot resources = new StandardRoot(context);
            resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",new File("target/classes").getAbsolutePath(), "/"));
            context.setResources(resources);

            tomcat.start();
            tomcat.getServer().await();
        } catch (ServletException | LifecycleException e) {
            e.printStackTrace();
        }

    }

}
