package com.whk.threadpool.handler;

import com.whk.dispatchprotocol.PlayerMessageRecord;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * 事件
 */
@Getter
@Setter
@Slf4j
public class PlayerMessageHandler extends AbstractMessageHandler {

    private Object message;

    private PlayerMessageRecord record;

    private long playerId;

    private boolean printTime = true;

    public PlayerMessageHandler(Object message, long playerId, PlayerMessageRecord record) {
        super(playerId);
        this.message = message;
        this.record = record;
        this.playerId = playerId;
    }

    @Override
    public ProcessorId getProcessorId() {
        return getRecord().processorId();
    }

    @Override
    public void run() {
        try {
            if (printTime) {
                long time = System.currentTimeMillis();
                record.method().invoke(record.clazz(), message, playerId);
                log.info("PlayerMessage %s exe time:%d%n".formatted(record.method().getName(), System.currentTimeMillis() - time));
            } else {
                record.method().invoke(record.clazz(), message, playerId);
            }
        } catch (Exception e) {
            assert e instanceof InvocationTargetException;
            InvocationTargetException exception = (InvocationTargetException) e;
            Throwable throwable = exception.getTargetException();
            log.error("PlayerMessage stack: ", throwable);
        }
    }

}
