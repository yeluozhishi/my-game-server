package com.whk.threadpool.handler;

import com.whk.threadpool.DriverInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractHandler implements Runnable{

    private IHandler record;

    private DriverInterface driver;


    public AbstractHandler(IHandler record) {
        this.record = record;
    }

}
