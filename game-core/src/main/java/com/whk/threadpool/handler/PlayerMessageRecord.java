package com.whk.threadpool.handler;

import com.whk.threadpool.ThreadType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public record PlayerMessageRecord(Method method, Object clazz, int messageId) implements IHandler {
    @Override
    public void doAction(Object... message) {
        try {
            method.invoke(clazz, message);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ThreadType threadType() {
        return ThreadType.PLAYER_THREAD;
    }
}

