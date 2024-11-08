package com.whk.listener.eventlistener.listener;

import com.whk.listener.eventlistener.IEvent;
import com.whk.threadpool.handler.IRecord;

public abstract class AbstractEventListener<T extends IEvent> implements IRecord {

    public abstract void dealEvent(T event);

    @Override
    public void doAction(Object... message) {
        T eventTmp = (T) message[0];
        if (check(eventTmp)){
            dealEvent(eventTmp);
        }
    }

    protected abstract boolean check(T event);
}
