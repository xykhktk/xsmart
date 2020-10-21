package org.xsmart.aspect;

import org.xsmart.system.annotation.Aspect;
import org.xsmart.system.annotation.Controller;
import org.xsmart.system.core.aop.AspectProxy;

import java.lang.reflect.Method;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    @Override
    public void before(Class<?> clz, Method method, Object[] params) throws Throwable {
        System.out.println("----- before ----- class name " + clz.getName());
        System.out.println("----- before ----- method" + method.getName());
    }

    @Override
    public void after(Class<?> clz, Method method, Object[] params) throws Throwable {
        System.out.println("----- after ----- class name " + clz.getName());
        System.out.println("----- after ----- method" + method.getName());
    }
}
