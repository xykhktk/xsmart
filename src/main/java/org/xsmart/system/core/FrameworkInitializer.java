package org.xsmart.system.core;

import org.xsmart.system.util.ClassUtil;

public class FrameworkInitializer {

    public static void init() {
//        Class<?>[] initClasses = {
//                ClassContainerBuilder.class,
//                BeanContainerBuilder.class,
//                AopInitializer.class,
//                IocInitializer.class,
//                ControllerContainerBuilder.class,
//        };
//        for (Class<?> clz : initClasses) {
//            ClassUtil.loadClass(clz.getName());
//        }

        ClassContainerBuilder classContainerBuilder  = new ClassContainerBuilder();
        BeanContainerBuilder beanContainerBuilder = new BeanContainerBuilder();
        AopInitializer aopInitializer = new AopInitializer();
        IocInitializer iocInitializer = new IocInitializer();
        ControllerContainerBuilder controllerContainerBuilder = new ControllerContainerBuilder();

        classContainerBuilder.init();
        beanContainerBuilder.init();
        aopInitializer.init();
        iocInitializer.init();
        controllerContainerBuilder.init();
    }

}
