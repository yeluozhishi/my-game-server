package com.whk.listener.eventlistener.listener;

import com.whk.listener.eventlistener.event.IEvent;

public abstract class IEventListener<T extends IEvent> implements IListener {

    abstract void dealEvent(T event);

    @Override
    public void eventProcess(Object event) {
        T eventTmp = (T) event;
        if (check(eventTmp)){
            dealEvent(eventTmp);
        }
    }

    protected abstract boolean check(T event);
}
