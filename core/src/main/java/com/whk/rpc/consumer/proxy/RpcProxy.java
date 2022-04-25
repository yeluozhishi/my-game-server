package com.whk.rpc.consumer.proxy;

import com.whk.rpc.annotation.NoReturnAndNonBlocking;
import com.whk.rpc.annotation.OnErrorContinue;
import com.whk.rpc.consumer.MessageCallBack;
import com.whk.rpc.consumer.MessageSendHandler;
import com.whk.rpc.consumer.RpcServerLoader;
import com.whk.rpc.model.MessageRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * 方法代理
 */
public class RpcProxy {

	public static <T> T create(Class<?> clazz, int serverId){
        //clazz传进来本身就是interface
        MethodProxy proxy = new MethodProxy(serverId);
        Class<?> [] interfaces = clazz.isInterface() ?
                                new Class[]{clazz} :
                                clazz.getInterfaces();
		return (T) Proxy.newProxyInstance(clazz.getClassLoader(),interfaces,proxy);
    }

	private static class MethodProxy implements InvocationHandler {
		private final Logger logger = Logger.getLogger(RpcProxy.class.getName());

		private int serverId;
		public MethodProxy(int serverId){
			this.serverId = serverId;
		}


		@Override
		public Object invoke(Object proxy, Method method, Object[] args)  throws Throwable {
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
		 * @param method
		 * @param args
		 * @return
		 */
		public Object rpcInvoke(Method method, Object[] args) throws InterruptedException, ExecutionException {
			var rpc = RpcServerLoader.getInstance();
			var messageSendHandler = rpc.getMessageSendHandler(serverId);
			if (messageSendHandler != null){
				//传输协议封装
				MessageRequest request = new MessageRequest();
				request.setMessageId(UUID.randomUUID().toString());
				request.setClassName(method.getDeclaringClass().getName());
				request.setMethodName(method.getName());
				request.setTypeParameters(method.getParameterTypes());
				request.setParametersVal(args);
				boolean nonBlocking = Objects.nonNull(method.getAnnotation(NoReturnAndNonBlocking.class));
				boolean onErrorContinue = Objects.nonNull(method.getAnnotation(OnErrorContinue.class));
				request.setNoReturnAndNonBlocking(nonBlocking);
				if (onErrorContinue) {
					try {
						return invocationSendHandler(request, messageSendHandler);
					} catch (Exception ex) {
						logger.severe("handleInvocation error: " + ex);
						return null;
					}
				}
				return invocationSendHandler(request, messageSendHandler);
			} else {
				return null;
			}
		}

		private Object invocationSendHandler(MessageRequest request, MessageSendHandler messageSendHandler) throws InterruptedException {
			if (request.isNoReturnAndNonBlocking()) {
				return messageSendHandler.sendRequestWithoutCallback(request);
			} else {
				MessageCallBack callBack = messageSendHandler.sendRequest(request);
				return callBack.start();
			}
		}

	}
}



