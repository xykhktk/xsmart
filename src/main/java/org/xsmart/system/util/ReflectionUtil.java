package org.xsmart.system.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {

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

    public static void setField(Object object, Field field,Object... arg){
        field.setAccessible(true);
        try {
            field.set(object,arg);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
