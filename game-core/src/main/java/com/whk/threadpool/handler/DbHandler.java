package com.whk.threadpool.handler;

public class DbHandler extends AbstractHandler {

    Runnable futureTask;

    public DbHandler(Runnable futureTask) {
        super(null);
        this.futureTask = futureTask;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        futureTask.run();
        System.out.println("db exeTime:" + (System.currentTimeMillis() - time));
    }
}
