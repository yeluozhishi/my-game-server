package com.whk.threadpool.dispatchprotocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface InstanceHandlerInterface {

    void doAction(Object... message) throws InvocationTargetException, IllegalAccessException;

    Object threadPoolExecutor();
}
