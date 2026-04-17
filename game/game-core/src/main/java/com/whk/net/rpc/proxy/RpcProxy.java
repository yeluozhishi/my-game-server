package com.whk.net.rpc.proxy;

import com.whk.net.rpc.annotation.MethodDescription;
import com.whk.net.rpc.api.IRpcService;
import com.whk.net.rpc.model.MessageRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;

/**
 * 方法代理
 */
@Slf4j
public class RpcProxy {

    public static <T extends IRpcService> T create(Class<T> clazz, String topic, long orderId) {
        //clazz传进来本身就是interface
        MethodProxy proxy = new MethodProxy(topic, orderId);
        Class<?>[] interfaces = clazz.isInterface() ? new Class[]{clazz} : clazz.getInterfaces();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, proxy);
    }

    private record MethodProxy(String topic, long orderId) implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            //如果传进来是一个已实现的具体类
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            } else {
                //如果传进来的是一个接口（核心)
                MethodDescription description = method.getAnnotation(MethodDescription.class);
                if (description.OnErrorContinue()) {
                    try {
                        return rpcInvoke(method, args, description);
                    } catch (Throwable t) {
                        log.error("%s, %s".formatted(t.getMessage(), t.getStackTrace()));
                    }
                } else {
                    return rpcInvoke(method, args, description);
                }
            }
            return null;
        }


        /**
         * 实现接口的核心方法
         *
         * @param method 方法
         * @param args   参数
         * @return Object
         */
        public Object rpcInvoke(Method method, Object[] args, MethodDescription description) {
            //传输协议封装
            if (Objects.nonNull(args)) {
                for (Object arg : args) {
                    if (arg != null && isImmutableCollection(arg)) {
                        log.error("检测到不可变集合参数 method: {}, arg className: {}", method.getName(), arg.getClass().getName());
                        return null;
                    }
                }
            }

            MessageRequest request = new MessageRequest();
            request.setClassName(method.getDeclaringClass().getName());
            request.setMethodName(method.getName());
            request.setParametersVal(args);
            request.setProcessorId(description.processorId());
            request.setNoReturnAndNonBlocking(description.NoReturnAndNonBlocking());
            request.setOrderId(orderId);
            return RpcProxyHolder.INSTANCE.sendRpcMessage(request, topic);
        }


        private boolean isImmutableCollection(Object obj) {
            String className = obj.getClass().getName();
            return className.startsWith("java.util.ImmutableCollections");
        }
    }
}



