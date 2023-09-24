package com.whk.net.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public abstract class ReceiverMessageService {

    public Logger logger = Logger.getLogger(ReceiverMessageService.class.getName());

    /**
     * 消费
     * @param record 记录
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public abstract void consume(ConsumerRecord<byte[], byte[]> record) throws InvocationTargetException, IllegalAccessException;
}
