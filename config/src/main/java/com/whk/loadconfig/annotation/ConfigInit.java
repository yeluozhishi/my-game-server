package com.whk.loadconfig.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * 初始化配置注解
 * @author Administrator
 */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigInit {

    /**
     * 优先级, 数值越大, 越早初始化
     */
    int priority() default 0;

    /**
     * 不在哪些服务器类型上执行初始化
     *
     * @see ServerEnum
     */
    ServerEnum[] notInServers() default {ServerEnum.WORLD_TYPE};

    String fileName();
}
