package com.whk.net.kafka;

import com.whk.net.dispatchprotocol.DispatchProtocolService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public abstract class ReceiverMessageService {

    public Logger logger = Logger.getLogger(ReceiverMessageService.class.getName());

    private DispatchProtocolService dispatchProtocolService;

    @Autowired
    public void setDispatchProtocolService(DispatchProtocolService dispatchProtocolService) {
        this.dispatchProtocolService = dispatchProtocolService;
    }

    public DispatchProtocolService getDispatchGameMessageService() {
        return dispatchProtocolService;
    }

    /**
     * 消费
     * @param record 记录
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public abstract void consume(ConsumerRecord<byte[], byte[]> record) throws InvocationTargetException, IllegalAccessException;
}
