package com.whk.listener.eventlistener.listener;

import com.whk.listener.eventlistener.event.IEvent;
import com.whk.threadpool.handler.HandlerInterface;

import java.util.concurrent.ThreadPoolExecutor;

public abstract class IEventListener<T extends IEvent> implements HandlerInterface {

    abstract void dealEvent(T event);

    @Override
    public void doAction(Object... message) {
        T eventTmp = (T) message[0];
        if (check(eventTmp)){
            dealEvent(eventTmp);
        }
    }

    @Override
    public ThreadPoolExecutor threadPoolExecutor() {
        throw new UnsupportedOperationException("不支持事件线程执行");
    }

    protected abstract boolean check(T event);
}
