package com.whk.dispatchprotocol;

import com.whk.threadpool.handler.InstanceHandlerInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;


public record MessageHandlerRecord(Method method, Object clazz, ThreadPoolExecutor threadPoolExecutor, int messageId) implements InstanceHandlerInterface {
    @Override
    public void doAction(Object... message) {
        try {
            method.invoke(clazz, message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

