package com.whk.net.rpc.annotation;

import com.whk.threadpool.processor.ProcessorId;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MethodDescription {

    ProcessorId processorId();

    /**
     * 没有返回值, 并且不需要阻塞执行完成的RPC请求可以使用此注解提升性能
     * 注意: 只能用在返回值为 java void的函数里,否则会出错
     */
    boolean NoReturnAndNonBlocking() default true;

    /**
     * 如果访问出错则返回null 然后继续执行后面的代码(慎用)
     */
    boolean OnErrorContinue() default false;
}
