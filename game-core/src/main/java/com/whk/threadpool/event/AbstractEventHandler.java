package com.whk.threadpool.event;

import com.whk.threadpool.dispatchprotocol.InstanceHandlerInterface;
import com.whk.threadpool.dispatchprotocol.MessageHandlerRecord;
import com.whk.threadpool.DriverInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEventHandler implements Runnable{

    private InstanceHandlerInterface record;

    private DriverInterface driverInterface;


    public AbstractEventHandler(InstanceHandlerInterface record) {
        this.record = record;
    }

}
