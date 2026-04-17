package com.whk.threadpool.handler;

import com.whk.threadpool.driver.IDriver;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractMessageHandler implements IQueueCommand{
    private long orderId;

    // 驱动器
    private IDriver driver;

    public AbstractMessageHandler(long orderId) {
        this.orderId = orderId;
    }
}
