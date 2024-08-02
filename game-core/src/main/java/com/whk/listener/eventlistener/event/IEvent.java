package com.whk.listener.eventlistener.event;

import com.whk.threadpool.TheadType;

public interface IEvent {
    long getOrderId();

    TheadType getTheadType();
}
