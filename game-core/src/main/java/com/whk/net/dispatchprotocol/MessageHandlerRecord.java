package com.whk.net.dispatchprotocol;

import com.whk.threadpool.ThreadPoolManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public record MessageHandlerRecord(Method method, Object clazz, ThreadPoolManager poolManager, int messageId) implements InstanceHandlerInterface {
    // todo 消息处理器加上线程池编号 修改注解GameMessageHandler 加上PoolId参数
    //private Integer PoolId;
    @Override
    public void doAction(Object message) throws InvocationTargetException, IllegalAccessException {

        method.invoke(clazz, message);
    }
}

