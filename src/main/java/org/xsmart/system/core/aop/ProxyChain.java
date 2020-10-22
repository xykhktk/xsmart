package org.xsmart.system.core.aop;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyChain {

    private Class<?> targetClass;
    private Object targetObject;
    private Method method;
    private MethodProxy methodProxy;
    private Object[] methodParams;

    private List<Proxy> proxyList;
    private int index;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method method, MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.method = method;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        Object result;
        if(index < proxyList.size())
        {
            result = proxyList.get(index++).doProxy(this);
        }else {
            result = methodProxy.invokeSuper(targetObject,methodParams);
        }
        return result;
    }
}
