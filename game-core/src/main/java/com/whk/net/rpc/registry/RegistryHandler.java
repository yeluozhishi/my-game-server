package com.whk.net.rpc.registry;

import com.whk.SpringUtils;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

@Slf4j
public class RegistryHandler {

    //用保存所有可用的服务
    private static final HashMap<String, Object> registryObject = new HashMap<>();
    private static final HashMap<String, Method> registryMethod = new HashMap<>();


    public RegistryHandler() {
        //完成rpc实例注册
        try {
            var beans = SpringUtils.getBeansWithAnnotation(RpcTag.class);
            for (Object obj : beans.values()) {
                String clazzName = obj.getClass().getInterfaces()[0].getName();
                registryObject.put(clazzName, obj);
                for (Method method : obj.getClass().getDeclaredMethods()) {
                    String methodName = getMethodName(clazzName, method.getName());
                    if (registryMethod.containsKey(methodName)) {
                        throw new Exception("不支持方法重载，重复注册方法%s".formatted(getMethodName(clazzName, method.getName())));
                    }
                    registryMethod.put(methodName, method);
                }
            }
            log.info("远程方法调用注册完成！");
        } catch (Exception e) {
            log.error("失败%s".formatted(e.getMessage()));
        }
    }

    private String getMethodName(String clazzName, String methodName) {
        return "%s.%s".formatted(clazzName, methodName);
    }

    public MessageResponse invokeMethod(Object msg) throws InvocationTargetException, IllegalAccessException {
        MessageResponse result = new MessageResponse();
        MessageRequest request = (MessageRequest) msg;
        //获取创建好的的类和方法
        //调用
        String methodName = getMethodName(request.getClassName(), request.getMethodName());
        if (registryObject.containsKey(request.getClassName()) && registryMethod.containsKey(methodName)) {
            Object clazz = registryObject.get(request.getClassName());
            Method method = registryMethod.get(methodName);
            result.setMessageId(request.getMessageId());
            Object re = method.invoke(clazz, request.getParametersVal());
            result.setResult(re);
        }
        return result;
    }

}
