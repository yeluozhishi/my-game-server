package com.whk.net.rpc.registry;

import com.whk.SpringUtils;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.model.MessageResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RegistryHandler {

    //用保存所有可用的服务
    private static final ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<>();


    public RegistryHandler() {
        //完成rpc实例注册
        try {
            var beans = SpringUtils.getBeansWithAnnotation(RpcTag.class);
            for (Object obj : beans.values()) {
                registryMap.put(obj.getClass().getInterfaces()[0].getName(), obj);
            }
        } catch (Exception e) {
            log.error("失败%s".formatted(e.getMessage()));
        }
    }

    public MessageResponse invokeMethod(Object msg) throws Exception {
        MessageResponse result = new MessageResponse();
        MessageRequest request = (MessageRequest) msg;
        //当客户端建立连接时，需要从自定义协议中获取信息，拿到具体的服务和实参
        //使用反射调用
        if (registryMap.containsKey(request.getClassName())) {
            Object clazz = registryMap.get(request.getClassName());
            Method method = clazz.getClass().getMethod(request.getMethodName(), request.getTypeParameters());
            result.setMessageId(request.getMessageId());
            Object re = method.invoke(clazz, request.getParametersVal());
            result.setResult(new Object[]{re});
        }
        return result;
    }

}
