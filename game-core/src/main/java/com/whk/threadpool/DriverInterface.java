package com.whk.threadpool;

import com.whk.threadpool.event.AbstractEventHandler;

public interface DriverInterface {
    public boolean isEmpty();

    public AbstractEventHandler poll();
}
