package com.whk.rpc.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
/*
  没有返回值, 并且不需要阻塞执行完成的RPC请求可以使用此注解提升性能
  注意: 只能用在返回值为 java void或scala Unit 的函数里,否则会出错
 */
public @interface NoReturnAndNonBlocking {

}
