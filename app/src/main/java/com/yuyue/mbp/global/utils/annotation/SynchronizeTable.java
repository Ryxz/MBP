package com.yuyue.mbp.global.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Renyx on 2018/6/7
 * 同步表注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SynchronizeTable {

    boolean synchronizable () default true;
}
