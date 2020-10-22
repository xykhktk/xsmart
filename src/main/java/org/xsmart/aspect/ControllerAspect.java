package org.xsmart.aspect;

import org.xsmart.system.annotation.Aspect;
import org.xsmart.system.annotation.Controller;
import org.xsmart.system.core.aop.AspectProxy;
import org.apache.log4j.Logger;
import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private Logger logger = Logger.getLogger(ControllerAspect.class);

    @Override
    public void before(Class<?> clz, Method method, Object[] params) throws Throwable {
        logger.debug("----- before ----- class name:" + clz.getName() + ",method:" + method.getName());
    }

    @Override
    public void after(Class<?> clz, Method method, Object[] params) throws Throwable {
        logger.debug("----- after ----- class name:" + clz.getName() + ",method:" + method.getName());
    }
}
