package com.whk.threadpool.handler;

import com.whk.threadpool.DriverInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractHandler implements Runnable{

    private HandlerInterface record;

    private DriverInterface driver;


    public AbstractHandler(HandlerInterface record) {
        this.record = record;
    }

}
