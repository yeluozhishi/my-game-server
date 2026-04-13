package com.whk.annotation;

import com.whk.threadpool.ThreadType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadAssign {
    ThreadType value() default ThreadType.PLAYER_THREAD;
}
