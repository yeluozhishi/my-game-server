package com.whk.rpc.until;

import java.util.concurrent.atomic.AtomicInteger;

public enum AutoIncrease {
    instance;

    private final AtomicInteger KEY_ORDER;

    AutoIncrease(){
        KEY_ORDER = new AtomicInteger(0);
    }

    public int getIncrease(){
        return KEY_ORDER.getAndIncrement();
    }
}
