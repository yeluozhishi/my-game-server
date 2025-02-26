package com.whk.threadpool.handler;

import com.whk.threadpool.processor.ProcessorId;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

@Slf4j
public class RPCMessageHandler extends AbstractHandler{

    private final Runnable futureTask;

    private final ProcessorId processorId;

    public RPCMessageHandler(String orderId, ProcessorId processorId, Runnable futureTask) {
        this.futureTask = futureTask;
        this.processorId = processorId;
        this.setOrderId(orderId);
    }

    @Override
    public void execute() throws InvocationTargetException, IllegalAccessException {
        long time = System.currentTimeMillis();
        futureTask.run();
        log.info("RPCMessage exe time:%d%n".formatted(System.currentTimeMillis() - time));
    }

    @Override
    public ProcessorId getProcessorId() {
        return processorId;
    }
}
