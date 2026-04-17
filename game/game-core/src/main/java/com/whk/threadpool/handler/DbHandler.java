package com.whk.threadpool.handler;


import com.whk.threadpool.processor.ProcessorId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DbHandler extends AbstractMessageHandler {

    private final Runnable futureTask;

    public DbHandler(long orderId, Runnable futureTask) {
        super(orderId);
        this.futureTask = futureTask;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        try {
            futureTask.run();
            log.info("db exe time:%d".formatted(System.currentTimeMillis() - time));
        } catch (Exception e) {
            log.error("db exe error:", e);
        }
    }


    @Override
    public ProcessorId getProcessorId() {
        return ProcessorId.DB_PROCESSOR;
    }
}
