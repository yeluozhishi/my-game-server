package com.whk.net.dispatchprotocol;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface InstanceHandlerInterface {

    public void doAction(Object message) throws InvocationTargetException, IllegalAccessException;
}
