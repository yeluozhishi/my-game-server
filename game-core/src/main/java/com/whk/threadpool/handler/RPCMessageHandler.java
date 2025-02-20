package com.whk.threadpool.handler;

public class RPCMessageHandler extends AbstractHandler{

    private final Runnable futureTask;

    public RPCMessageHandler(String orderId, Runnable futureTask) {
        super(null);
        this.futureTask = futureTask;
        this.setOrderId(orderId);
    }

    @Override
    public void run() {
        futureTask.run();
    }
}
