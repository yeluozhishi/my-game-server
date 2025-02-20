package com.whk.threadpool.handler;

import com.whk.threadpool.IDriver;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;

/**
 * 分为自定义存放的数据，执行方法，队列
 */
@Getter
@Setter
public abstract class AbstractHandler implements Runnable{

    private String orderId;

    // 执行方法
    private IRecord record;

    // 队列
    private IDriver driver;


    public AbstractHandler(IRecord record) {
        this.record = record;
    }

    public abstract ProcessorId getProcessorId();
}
