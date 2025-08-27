package com.whk;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DBAroundAnnotation {
    boolean hasReturn() default true;
}
