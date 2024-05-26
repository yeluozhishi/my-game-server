package com.whk.threadpool.event;

import com.whk.net.dispatchprotocol.MessageHandlerRecord;
import com.whk.threadpool.DriverInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEventHandler implements Runnable{

    private MessageHandlerRecord record;

    private DriverInterface driverInterface;


    public AbstractEventHandler(MessageHandlerRecord record) {
        this.record = record;
    }

}
