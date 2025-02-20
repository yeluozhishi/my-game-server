package com.whk.net.rpc.proxy;

import com.whk.net.rpc.annotation.MethodDescription;
import com.whk.net.rpc.model.MessageRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

/**
 * 方法代理
 */
public class RpcProxy {

    public static Object create(Class<?> clazz, String topic, String orderId) {
        //clazz传进来本身就是interface
        MethodProxy proxy = new MethodProxy(topic, orderId);
        Class<?>[] interfaces = clazz.isInterface() ? new Class[]{clazz} : clazz.getInterfaces();
        return Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, proxy);
    }

    private static class MethodProxy implements InvocationHandler {
        private final Logger logger = Logger.getLogger(RpcProxy.class.getName());

        private final String topic;

        private final String orderId;

        public MethodProxy(String topic, String orderId) {
            this.topic = topic;
            this.orderId = orderId;
        }


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            //如果传进来是一个已实现的具体类
            if (Object.class.equals(method.getDeclaringClass())) {
                try {
                    return method.invoke(this, args);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                //如果传进来的是一个接口（核心)
            } else {
                return rpcInvoke(method, args);
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
        public Object rpcInvoke(Method method, Object[] args) {
            //传输协议封装
            MessageRequest request = new MessageRequest();
            request.setClassName(method.getDeclaringClass().getName());
            request.setMethodName(method.getName());
            request.setTypeParameters(method.getParameterTypes());
            request.setParametersVal(args);
            MethodDescription description = method.getAnnotation(MethodDescription.class);
            request.setProcessorId(description.processorId());
            request.setNoReturnAndNonBlocking(description.NoReturnAndNonBlocking());
            request.setOrderId(orderId);
            if (description.OnErrorContinue()) {
                try {
                    return RpcProxyHolder.INSTANCE.sendRpcMessage(request, topic);
                } catch (Exception ex) {
                    logger.severe("handleInvocation error: " + ex);
                    return null;
                }
            }
            return RpcProxyHolder.INSTANCE.sendRpcMessage(request, topic);
        }

    }
}



