package org.xsmart.system.core.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyManager {

    @SuppressWarnings("unchecked")
    public static  <T> T createProxy(Class<T> targetClass, List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass,new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,o,method,methodProxy,objects,proxyList);
            }
        });

    }

}
