package com.whk.threadpool.processor;

import com.whk.threadpool.handler.AbstractHandler;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * 作用：数据分流。将消息交予对应的处理器。
 */
public enum ProcessorManager {
    INSTANCE;

    private final Logger logger = Logger.getLogger(ProcessorManager.class.getName());

    // 玩家消息处理器
    @Getter
    private final Map<ProcessorId, AbstractMessageProcessor> messageProcessors = new HashMap<>();

    public void process(ProcessorId processorId, AbstractHandler handler) {
        var processor = messageProcessors.get(processorId);
        if (Objects.isNull(processor)) {
            logger.severe("处理器不存在：%s".formatted(processorId));
            return;
        }
        processor.message(handler);
    }

    public void addProcessor(ProcessorId processorId, AbstractMessageProcessor processor) {
        messageProcessors.put(processorId, processor);
    }
}
