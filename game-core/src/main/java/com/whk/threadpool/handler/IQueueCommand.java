package com.whk.threadpool.handler;

import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.processor.ProcessorId;

/**
 * 句柄
 * 分为自定义数据，执行方法，队列
 */
public interface IQueueCommand extends Runnable{

    String getOrderId();

    IDriver getDriver();

    void setDriver(IDriver driver);

    ProcessorId getProcessorId();

}
