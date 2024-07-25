package com.whk.threadpool.messagehandler;

import com.whk.threadpool.dispatchprotocol.InstanceHandlerInterface;
import com.whk.threadpool.DriverInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMessageHandler implements Runnable{

    private InstanceHandlerInterface record;

    private DriverInterface driverInterface;


    public AbstractMessageHandler(InstanceHandlerInterface record) {
        this.record = record;
    }

}
