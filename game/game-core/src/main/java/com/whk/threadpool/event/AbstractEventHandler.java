package com.whk.threadpool.event;

import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.handler.IQueueCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public abstract class AbstractEventHandler implements IQueueCommand {
    private String orderId;
    // 驱动器
    private IDriver driver;

    public abstract void doAction();

    public AbstractEventHandler(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        try {
            doAction();
            long diff = System.currentTimeMillis() - time;
            if (diff >= 200) log.info("Event exe time:%d".formatted(diff));
        } catch (Exception e) {
            log.error("Event exe error:", e);
        }

    }
}
