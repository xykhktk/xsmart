package org.xsmart.system.core;

import org.xsmart.system.util.ReflectionUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BeanContainerBuilder {

    private static Map<Class<?>,Object> BEAN_MAP = new HashMap<>();

    static {
        HashSet<Class<?>> classSet = ClassContainerBuilder.getBeans();
        for(Class<?> clz : classSet){
            Object o = ReflectionUtil.getInstance(clz);
            BEAN_MAP.put(clz,o);
        }
    }

    public static Map<Class<?>,Object> getBeanContainer(){
        return  BEAN_MAP;
    }

    public static <T> T getBean(Class<T> clz) {
        if(!BEAN_MAP.containsKey(clz)){
            throw new RuntimeException("can not get bean by");
        }
        return (T) BEAN_MAP.get(clz);
    }

    public static void setBeanMap(Class<?> clz,Object o){
        BEAN_MAP.put(clz,o);
    }
}
