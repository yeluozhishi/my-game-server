package com.whk.threadpool.processor;

import com.whk.threadpool.handler.IQueueCommand;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 作用：数据分流。将消息交予对应的处理器。
 */
@Getter
@Slf4j
public enum ProcessorManager {
    INSTANCE;

    // 消息处理器
    private final Map<ProcessorId, AbstractMessageProcessor<? extends IQueueCommand>> messageProcessors = new HashMap<>();

    public <T extends IQueueCommand> void process(T handler) {
        if (Objects.isNull(handler)) return;
        var processor = messageProcessors.get(handler.getProcessorId());
        if (Objects.isNull(processor)) {
            log.error("处理器不存在：%s, %s".formatted(handler.getProcessorId(), handler));
            return;
        }
        processor.message(handler);
    }

    public void addProcessor(ProcessorId processorId, AbstractMessageProcessor<? extends IQueueCommand> processor) {
        messageProcessors.put(processorId, processor);
    }

    public void removeDriver(ProcessorId processorId, String driverId) {
        var processor = messageProcessors.get(processorId);
        if (Objects.nonNull(processor)) {
            processor.removeDriver(driverId);
        }
    }

    public void stop() {
        for (var processor : messageProcessors.values()) {
            processor.stop();
        }
    }
}
