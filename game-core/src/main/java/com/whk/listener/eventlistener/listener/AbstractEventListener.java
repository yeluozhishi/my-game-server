package com.whk.listener.eventlistener.listener;

import com.whk.listener.eventlistener.IEvent;

public abstract class AbstractEventListener<T extends IEvent> {

    public abstract void dealEvent(T event);

    public void doAction(Object... message) {
        dealEvent((T) message[0]);
    }

}
