package com.whk.threadpool.db;

import com.whk.threadpool.handler.AbstractHandler;
import com.whk.threadpool.handler.HandlerInterface;

public class DbHandler extends AbstractHandler {
    public DbHandler(HandlerInterface record) {
        super(record);
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        getRecord().doAction();
        System.out.println("db exeTime:" + (System.currentTimeMillis() - time));
    }
}
