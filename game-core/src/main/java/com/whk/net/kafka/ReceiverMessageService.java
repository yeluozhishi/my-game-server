package com.whk.net.kafka;

import com.whk.net.dispatchmessage.DispatchGameMessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

public abstract class ReceiverMessageService {

    private DispatchGameMessageService dispatchGameMessageService;

    @Autowired
    public void setDispatchGameMessageService(DispatchGameMessageService dispatchGameMessageService) {
        this.dispatchGameMessageService = dispatchGameMessageService;
    }

    public DispatchGameMessageService getDispatchGameMessageService() {
        return dispatchGameMessageService;
    }

    public abstract void consume(ConsumerRecord<String, byte[]> record) throws InvocationTargetException, IllegalAccessException;
}
