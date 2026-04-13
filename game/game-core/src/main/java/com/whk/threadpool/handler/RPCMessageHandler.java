package com.whk.threadpool.handler;

import com.whk.net.rpc.model.MessageRequest;
import com.whk.net.rpc.proxy.RpcProxyHolder;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

@Getter
@Setter
@Slf4j
public class RPCMessageHandler extends AbstractMessageHandler {

    private final ProcessorId processorId;
    private final MessageRequest request;

    public RPCMessageHandler(MessageRequest request) {
        super(request.getOrderId());
        this.processorId = request.getProcessorId();
        this.request = request;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        try {
            if (request.isNoReturnAndNonBlocking()) {
                RpcProxyHolder.INSTANCE.getRegistryHandler().invokeMethod(request);
            } else {
                var response = RpcProxyHolder.INSTANCE.getRegistryHandler().invokeMethod(request);
                response.setTopic(request.getResponseTopic());
                RpcProxyHolder.INSTANCE.getRpcService().sendRpcResponse(response);
            }
            log.info("RPCMessage exe time:%d%n".formatted(System.currentTimeMillis() - time));
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException exception = (InvocationTargetException) e;
            Throwable throwable = exception.getTargetException();
            log.error("RpcRequest error:", throwable);
        }
    }

    @Override
    public ProcessorId getProcessorId() {
        return processorId;
    }
}
