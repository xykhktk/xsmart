package org.xsmart.enhance.aspect;

import org.apache.log4j.Logger;
import org.xsmart.enhance.annotation.Log2;
import org.xsmart.system.annotation.Aspect;
import org.xsmart.system.core.aop.AspectProxy;

import java.lang.reflect.Method;

@Aspect(Log2.class)
public class Log2Aspect extends AspectProxy {

    private Logger logger = Logger.getLogger(Log2Aspect.class);

    @Override
    public void before(Class<?> clz, Method method, Object[] params) throws Throwable {
        logger.debug("----- log2 before ----- class name:" + clz.getName() + ",method:" + method.getName());
    }

    @Override
    public void after(Class<?> clz, Method method, Object[] params) throws Throwable {
        logger.debug("----- log2 after ----- class name:" + clz.getName() + ",method:" + method.getName());
    }
}
