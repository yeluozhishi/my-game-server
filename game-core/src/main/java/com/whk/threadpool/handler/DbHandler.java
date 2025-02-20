package com.whk.threadpool.handler;


public class DbHandler extends AbstractHandler {

    private final Runnable futureTask;

    public DbHandler(String orderId, Runnable futureTask) {
        super(null);
        this.futureTask = futureTask;
        setOrderId(orderId);
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        futureTask.run();
        System.out.printf("db exeTime:%d%n", System.currentTimeMillis() - time);
    }
}
