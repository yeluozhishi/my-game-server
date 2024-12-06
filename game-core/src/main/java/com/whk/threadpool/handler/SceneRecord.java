package com.whk.threadpool.handler;


public record SceneRecord(Runnable runnable) implements IRecord {
    @Override
    public void doAction(Object... message) {
        runnable.run();
    }

}

