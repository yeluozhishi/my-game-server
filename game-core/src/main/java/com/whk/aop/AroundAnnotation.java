package com.whk.aop;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AroundAnnotation {
    boolean hasReturn() default true;
}
