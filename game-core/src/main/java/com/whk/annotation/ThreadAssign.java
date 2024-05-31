package com.whk.annotation;

import com.whk.threadpool.ThreadPoolManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadAssign {
    ThreadPoolManager value() default ThreadPoolManager.PLAYER_THREAD;
}
