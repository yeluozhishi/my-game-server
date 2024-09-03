package com.whk.threadpool.handler;

public class DbHandler extends AbstractHandler {
    public DbHandler(IHandler record) {
        super(record);
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        getRecord().doAction();
        System.out.println("db exeTime:" + (System.currentTimeMillis() - time));
    }
}
