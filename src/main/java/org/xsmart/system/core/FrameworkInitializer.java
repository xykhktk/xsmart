package org.xsmart.system.core;

import org.xsmart.system.util.ClassUtil;

public class FrameworkInitializer {

    public static void init() {
        Class<?>[] initClasses = {
                ClassContainerBuilder.class,
                BeanContainerBuilder.class,
                AopInitializer.class,
                IocInitializer.class,
                ControllerContainerBuilder.class,
        };
        for (Class<?> clz : initClasses) {
            ClassUtil.loadClass(clz.getName());
        }

    }

}
