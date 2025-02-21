package com.whk.threadpool.handler;

import com.whk.dispatchprotocol.PlayerMessageRecord;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;

/**
 * 事件
 */
@Getter
@Setter
public class PlayerMessageHandler extends AbstractHandler {

    private Object message;

    private PlayerMessageRecord record;

    private long playerId;

    public PlayerMessageHandler(Object message, long playerId, PlayerMessageRecord record) {
        this.message = message;
        this.record = record;
        this.playerId = playerId;
        this.setOrderId(String.valueOf(playerId));
    }

    @Override
    public ProcessorId getProcessorId() {
        return getRecord().processorId();
    }

    @Override
    public void execute() throws InvocationTargetException, IllegalAccessException {
        long time = System.currentTimeMillis();
        record.method().invoke(record.clazz(), message, playerId);
        logger.info("PlayerMessage exe time:%d%n".formatted(System.currentTimeMillis() - time));
    }
}
