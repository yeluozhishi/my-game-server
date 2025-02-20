package com.whk.threadpool.handler;

import com.whk.threadpool.processor.ProcessorId;

public class RPCMessageHandler extends AbstractHandler{

    private final Runnable futureTask;

    private final ProcessorId processorId;

    public RPCMessageHandler(String orderId, ProcessorId processorId, Runnable futureTask) {
        super(null);
        this.futureTask = futureTask;
        this.processorId = processorId;
        this.setOrderId(orderId);
    }

    @Override
    public void run() {
        futureTask.run();
    }

    @Override
    public ProcessorId getProcessorId() {
        return processorId;
    }
}
