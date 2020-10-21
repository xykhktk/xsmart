package org.xsmart.system.core;

import org.xsmart.system.annotation.Aspect;
import org.xsmart.system.core.aop.AspectProxy;
import org.xsmart.system.core.aop.Proxy;
import org.xsmart.system.core.aop.ProxyManager;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class AopInitializer {

    public void init(){
        Map<Class<?>,Set<Class<?>>> proxyMap = createProxyMap();
        try {
            Map<Class<?>, List<Proxy>> targetClassAndProxyObjectMap = createTargetMap(proxyMap);
            for(Map.Entry<Class<?>, List<Proxy>> targetClassAndProxyInstanceMap: targetClassAndProxyObjectMap.entrySet()){
                Class<?> targetClass = targetClassAndProxyInstanceMap.getKey();
                List<Proxy> proxyList = targetClassAndProxyInstanceMap.getValue();
                Object proxy = ProxyManager.createProxy(targetClass,proxyList);
                BeanContainerBuilder.setBeanMap(targetClass,proxy);
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    private static Set<Class<?>> creatTargetClassSet(Aspect aspect){
        Set<Class<?>> result = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)){
            result.addAll(ClassContainerBuilder.getClassSetByAnnotation(annotation));
        }
        return result;
    }

    private static Map<Class<?>,Set<Class<?>>> createProxyMap(){
        Map<Class<?>,Set<Class<?>>> resultMap = new HashMap<>();
        HashSet<Class<?>> proxySet = ClassContainerBuilder.getClassSetBySuper(AspectProxy.class);
        for(Class<?> proxyClass : proxySet){
            Aspect aspect = proxyClass.getAnnotation(Aspect.class);
            Set<Class<?>> targetClassSet = creatTargetClassSet(aspect);
            resultMap.put(proxyClass,targetClassSet);
        }
        return resultMap;
    }

    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxyInstance = (Proxy)proxyClass.getDeclaredConstructor().newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxyInstance);
                }else{
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxyInstance);
                    targetMap.put(targetClass,proxyList);
                }
            }
        }
        return targetMap;
    }
}
