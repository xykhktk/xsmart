package org.xsmart.system.util;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {

    private static Logger logger = Logger.getLogger(PropsUtil.class);

    public static Object getInstance(Class<?> clazz){
        Object instance = new Object();
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static Object invokeMethod(Object object, Method method,Object... arg){
        Object invokeResult = new Object();
        method.setAccessible(true);
        try {
            invokeResult = method.invoke(object,arg);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return invokeResult;
    }

    public static void setField(Object object, Field field,Object arg){
        try {
            field.setAccessible(true);
            field.set(object,arg);
        } catch (IllegalAccessException e) {
            logger.error("ReflectionUtil setField error :" + e.getMessage());
            e.printStackTrace();
        }
    }


}
