package org.xsmart.system.core.aop;

import org.apache.log4j.Logger;
import org.xsmart.system.util.ClassUtil;

import java.lang.reflect.Method;

public class AspectProxy implements Proxy{

    private static Logger logger = Logger.getLogger(ClassUtil.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = new Object();

        Class<?> clz = proxyChain.getTargetClass();
        Method method = proxyChain.getMethod();
        Object[] params =proxyChain.getMethodParams();

        try{
            if(intercept(clz,method,params)){
                before(clz,method,params);
                result = proxyChain.doProxyChain();
                after(clz,method,params);
            }else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e){
            logger.error("AspectProxy doProxy error :" + e.getMessage());
            error(clz,method,params);
        }finally {
            end();
        }


        return result;
    }

    public void begin(){

    }

    public boolean intercept(Class<?> clz, Method method,Object[] params) throws Throwable{
        return true;
    }

    public void before(Class<?> clz, Method method,Object[] params) throws Throwable{

    }

    public void after(Class<?> clz, Method method,Object[] params)throws Throwable{

    }

    public void error(Class<?> clz, Method method,Object[] params)throws Throwable{

    }

    public void end(){

    }


}
