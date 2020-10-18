package org.xsmart.system.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * todo 先参考《架构探险》用action注解处理请求，后面再改成类似spring boot的 PostMapping之类
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
    String value();
}
