package com.ecwalk.common.other.spring.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//只能在该类使用
@Retention(RetentionPolicy.RUNTIME)//表示在运行时通过反射获得载体
@Documented//javadoc 载体
public @interface SpringController {
	String value() default "";
}
