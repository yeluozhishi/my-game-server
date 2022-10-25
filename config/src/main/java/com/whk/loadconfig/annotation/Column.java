package com.whk.loadconfig.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * 特殊处理的列
 * @author Administrator
 */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    ColumnType column();
}
