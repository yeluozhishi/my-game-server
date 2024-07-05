package com.whk.threadpool.dispatchprotocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;


public record MessageHandlerRecord(Method method, Object clazz, ThreadPoolExecutor threadPoolExecutor, int messageId) implements InstanceHandlerInterface {
    @Override
    public void doAction(Object... message) throws InvocationTargetException, IllegalAccessException {
        method.invoke(clazz, message);
    }
}

