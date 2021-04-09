package org.xsmart.enhance.aspect;

import org.xsmart.enhance.annotation.Log1;
import org.xsmart.system.annotation.Aspect;
import org.xsmart.system.annotation.Controller;
import org.xsmart.system.core.aop.AspectProxy;
import org.apache.log4j.Logger;
import java.lang.reflect.Method;

@Aspect(Log1.class)
public class Log1Aspect extends AspectProxy {

    private Logger logger = Logger.getLogger(Log1Aspect.class);

    @Override
    public void before(Class<?> clz, Method method, Object[] params) throws Throwable {
        logger.debug("----- log1 before ----- class name:" + clz.getName() + ",method:" + method.getName());
    }

    @Override
    public void after(Class<?> clz, Method method, Object[] params) throws Throwable {
        logger.debug("----- log1 after ----- class name:" + clz.getName() + ",method:" + method.getName());
    }
}
