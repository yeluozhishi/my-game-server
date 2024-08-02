package com.whk.dispatchprotocol;

import com.whk.threadpool.handler.HandlerInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadPoolExecutor;


public record MessageHandlerRecord(Method method, Object clazz, ThreadPoolExecutor threadPoolExecutor, int messageId) implements HandlerInterface {
    @Override
    public void doAction(Object... message) {
        try {
            method.invoke(clazz, message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

