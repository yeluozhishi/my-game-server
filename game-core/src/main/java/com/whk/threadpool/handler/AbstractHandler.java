package com.whk.threadpool.handler;

import com.whk.threadpool.DriverInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractHandler implements Runnable{

    private InstanceHandlerInterface record;

    private DriverInterface driver;


    public AbstractHandler(InstanceHandlerInterface record) {
        this.record = record;
    }

}
