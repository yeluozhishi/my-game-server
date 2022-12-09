package com.whk.rpc.consumer.proxy;

import com.whk.rpc.annotation.NoReturnAndNonBlocking;
import com.whk.rpc.annotation.OnErrorContinue;
import com.whk.rpc.model.MessageRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * 方法代理
 */
public class RpcProxy {

	public static <T> T create(Class<?> clazz, String instanceId){
        //clazz传进来本身就是interface
        MethodProxy proxy = new MethodProxy(instanceId);
        Class<?> [] interfaces = clazz.isInterface() ?
                                new Class[]{clazz} :
                                clazz.getInterfaces();
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(),interfaces,proxy);
    }

	private static class MethodProxy implements InvocationHandler {
		private final Logger logger = Logger.getLogger(RpcProxy.class.getName());

		private final String instanceId;
		public MethodProxy(String instanceId){
			this.instanceId = instanceId;
		}


		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			//如果传进来是一个已实现的具体类（本次演示略过此逻辑)
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
		 * @param method 方法
		 * @param args 参数
		 * @return Object
		 */
		public Object rpcInvoke(Method method, Object[] args) {
			//传输协议封装
			MessageRequest request = new MessageRequest();
			request.setInstanceId(instanceId);
			request.setClassName(method.getDeclaringClass().getName());
			request.setMethodName(method.getName());
			request.setTypeParameters(method.getParameterTypes());
			request.setParametersVal(args);
			boolean nonBlocking = Objects.nonNull(method.getAnnotation(NoReturnAndNonBlocking.class));
			boolean onErrorContinue = Objects.nonNull(method.getAnnotation(OnErrorContinue.class));
			request.setNoReturnAndNonBlocking(nonBlocking);
			if (onErrorContinue) {
				try {
					return RpcProxyHolder.INSTANCE.sendRpcMessage(request, request.isNoReturnAndNonBlocking());
				} catch (Exception ex) {
					logger.severe("handleInvocation error: " + ex);
					return null;
				}
			}
			return RpcProxyHolder.INSTANCE.sendRpcMessage(request, request.isNoReturnAndNonBlocking());
		}

	}
}



