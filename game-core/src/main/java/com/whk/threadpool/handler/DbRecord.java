package com.whk.threadpool.handler;

import com.whk.threadpool.ThreadType;


public record DbRecord(Runnable runnable) implements IHandler {
    @Override
    public void doAction(Object... message) {
        runnable.run();
    }

    @Override
    public ThreadType threadType() {
        return ThreadType.DB_THREAD;
    }
}

