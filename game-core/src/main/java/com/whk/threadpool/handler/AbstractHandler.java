package com.whk.threadpool.handler;

import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 句柄
 * 分为自定义数据，执行方法，队列
 */
@Getter
@Setter
@Slf4j
public abstract class AbstractHandler implements Runnable{

    private String orderId;

    // 驱动器
    private IDriver driver;

    public abstract ProcessorId getProcessorId();

}
