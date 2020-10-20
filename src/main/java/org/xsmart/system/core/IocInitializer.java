package org.xsmart.system.core;

import org.xsmart.system.annotation.Autowire;
import org.xsmart.system.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

public class IocInitializer {

    static {
        Map<Class<?>,Object> bean_container =  BeanContainerBuilder.getBeanContainer();

        if (!bean_container.isEmpty()) {
            for (Map.Entry<Class<?>, Object> beanEntry : bean_container.entrySet()) {

                Class<?> clz = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] fields = clz.getFields();

                if (fields.length == 0) {
                    continue;
                }

                for (Field beanFiled : fields) {
                    if (!beanFiled.isAnnotationPresent(Autowire.class)) {
                        continue;
                    }

                    Class<?> beanClass = beanFiled.getType();
                    Object autoWireInstance = bean_container.get(beanClass);
                    if (autoWireInstance != null) {
                        ReflectionUtil.setField(beanInstance, beanFiled, autoWireInstance);
                    }
                }
            }
        }

    }

}
