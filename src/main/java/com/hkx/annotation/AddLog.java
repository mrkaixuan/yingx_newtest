package com.hkx.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解,添加在方法上,运行期有效
 */
@Target(ElementType.METHOD)//添加到方法上
@Retention(RetentionPolicy.RUNTIME)//在运行期有效
//@Documented
//@Inherited
public @interface AddLog {
    String value();
    String name() default "";
}
