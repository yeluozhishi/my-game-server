package com.whk.net.dispatchmessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


record InstanceHandlerRecord(Method method, Object clazz, String clazzName) {

    public void invoke(Object message) throws InvocationTargetException, IllegalAccessException {
        method.invoke(clazz, message);
    }

    public int getMessageId(){
        // 协议号前面部分
        var pre = Integer.parseInt(clazzName.split("_")[1]) * DispatchGameMessageService.messageSize;
        // 协议号后面部分
        var end = Integer.parseInt(method.getName().split("_")[1]);
        return pre + end;
    }
}
