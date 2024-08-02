package com.whk.threadpool.db;

import com.whk.threadpool.handler.AbstractHandler;
import com.whk.threadpool.handler.InstanceHandlerInterface;

public class DbHandler extends AbstractHandler {
    public DbHandler(InstanceHandlerInterface record) {
        super(record);
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        getRecord().doAction();
        System.out.println("db exeTime:" + (System.currentTimeMillis() - time));
    }
}
