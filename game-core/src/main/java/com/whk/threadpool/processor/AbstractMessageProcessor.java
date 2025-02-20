package com.whk.threadpool.processor;

import com.whk.threadpool.handler.AbstractHandler;

public abstract class AbstractMessageProcessor<T extends AbstractHandler> implements IProcessor<T> {
    public <T2 extends AbstractHandler> void message(T2 handler) {
        message0((T) handler);
    }

}
