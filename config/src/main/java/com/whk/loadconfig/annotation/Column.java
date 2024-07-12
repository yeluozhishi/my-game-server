package com.whk.loadconfig.annotation;

import com.whk.loadconfig.convert.IConvert;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 特殊处理的列
 * @author Administrator
 */
@Target({TYPE,FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

     Class<? extends IConvert> converter();

     boolean ignore() default false;
}
