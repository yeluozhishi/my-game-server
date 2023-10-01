package com.whk.net.dispatchprotocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


record InstanceHandlerRecord(Method method, Object clazz, String clazzName, int messageId) {

    public void invoke(Object message) throws InvocationTargetException, IllegalAccessException {

        method.invoke(clazz, message);
    }
}
