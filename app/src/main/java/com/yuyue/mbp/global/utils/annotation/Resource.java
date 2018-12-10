package com.yuyue.mbp.global.utils.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Renyx on 2018/6/7
 * 用于注入的注解
 */
@Target({TYPE,FIELD,METHOD})
@Retention(RUNTIME)
public @interface Resource {

    String name () default "";
}
