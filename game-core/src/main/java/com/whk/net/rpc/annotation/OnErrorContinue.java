package com.whk.net.rpc.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
/*
  如果访问出错则返回null 然后继续执行后面的代码(慎用)
 */
public @interface OnErrorContinue {
}
