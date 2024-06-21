package com.whk.rpc.registry;

import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.model.MessageResponse;
import org.whk.classScan.ScannerClassUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class RegistryHandler {

    private final Logger logger = Logger.getLogger(RegistryHandler.class.getName());

    //用保存所有可用的服务
    private static final ConcurrentHashMap<String, Object> registryMap = new ConcurrentHashMap<>();


    public RegistryHandler(String position) {
        //完成递归扫描
        try {
            List<Class<?>> classList = ScannerClassUtil.INSTANCE.scanClassFile(position, null);
            for (Class<?> clazz : classList) {
                registryMap.put(clazz.getInterfaces()[0].getName(), clazz.getConstructors()[0].newInstance());

            }
        } catch (Exception e) {
            logger.severe("失败%s".formatted(e.getMessage()));
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
            result.setError("");
            var re = method.invoke(clazz, request.getParametersVal());
            result.setResult(new Object[]{re});
        }
        return result;
    }

}
