package com.whk.annotation;

import com.whk.threadpool.processor.ProcessorId;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerDescription {
    // 编号
    int number();

    // 描述
    String desc() default "";

    // 处理器
    ProcessorId processorId() default ProcessorId.PLAYER_PROCESSOR;
}
