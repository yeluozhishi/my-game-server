package com.whk.annotation;

import com.whk.threadpool.TheadType;
import com.whk.threadpool.ThreadPoolManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ThreadPoolExecutor;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadAssign {
    TheadType value() default TheadType.PLAYER_THREAD;
}
