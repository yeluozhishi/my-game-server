package com.whk.threadpool.handler;

import com.whk.threadpool.ThreadType;


public record SceneRecord(Runnable runnable) implements IHandler {
    @Override
    public void doAction(Object... message) {
        runnable.run();
    }

    @Override
    public ThreadType threadType() {
        return ThreadType.SCENE_THREAD;
    }
}

