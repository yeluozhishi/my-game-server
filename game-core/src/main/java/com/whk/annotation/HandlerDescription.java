package com.whk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface HandlerDescription {
    // 编号
    String number() default "";
    // 描述
    String desc() default "";
}
