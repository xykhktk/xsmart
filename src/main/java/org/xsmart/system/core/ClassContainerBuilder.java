package org.xsmart.system.core;

import org.xsmart.system.annotation.Controller;
import org.xsmart.system.annotation.Service;
import org.xsmart.system.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;

public class ClassContainerBuilder {

    private static HashSet<Class<?>> CLASS_CONTAINER = new HashSet<>();

    static {
        //todo 暂时写死
        CLASS_CONTAINER = ClassUtil.addPackageClass("org.xsmart");
    }

    public static HashSet<Class<?>> getClassContainer(){
        return CLASS_CONTAINER;
    }

    public static HashSet<Class<?>> getServiceClass(){
        HashSet<Class<?>> resultSet = new HashSet<>();
        for(Class<?> clz : CLASS_CONTAINER){
            if(clz.isAnnotationPresent(Service.class)){
                resultSet.add(clz);
            }
        }
        return resultSet;
    }

    public static HashSet<Class<?>> getControllerClass(){
        HashSet<Class<?>> resultSet = new HashSet<>();
        for(Class<?> clz : CLASS_CONTAINER){
            if(clz.isAnnotationPresent(Controller.class)){
                resultSet.add(clz);
            }
        }
        return resultSet;
    }

    public static HashSet<Class<?>> getBeans(){
        HashSet<Class<?>> resultSet = new HashSet<>();
        resultSet.addAll(getControllerClass());
        resultSet.addAll(getServiceClass());
        return resultSet;
    }

    public static HashSet<Class<?>> getClassSetBySuper(Class<?> superClass){
        HashSet<Class<?>> resultSet = new HashSet<>();
        for(Class<?> clz : CLASS_CONTAINER){
            if(superClass.isAssignableFrom(clz) && !superClass.equals(clz)){
                resultSet.add(clz);
            }
        }
        return resultSet;
    }

    public static HashSet<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass){
        HashSet<Class<?>> resultSet = new HashSet<>();
        for(Class<?> clz : CLASS_CONTAINER){
            if(clz.isAnnotationPresent(annotationClass)){
                resultSet.add(clz);
            }
        }
        return resultSet;
    }

}
