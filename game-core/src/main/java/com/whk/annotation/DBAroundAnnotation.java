package com.whk.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DBAroundAnnotation {
    boolean hasReturn() default true;
}
