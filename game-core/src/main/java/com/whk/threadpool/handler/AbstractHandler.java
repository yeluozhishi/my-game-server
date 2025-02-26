package com.whk.threadpool.handler;

import com.whk.threadpool.IDriver;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

/**
 * 句柄
 * 分为自定义数据，执行方法，队列
 */
@Getter
@Setter
@Slf4j
public abstract class AbstractHandler implements Runnable{

    private String orderId;

    // 队列
    private IDriver driver;

    public abstract ProcessorId getProcessorId();

    public abstract void execute() throws InvocationTargetException, IllegalAccessException;

    @Override
    public void run() {
        try {
            execute();
        } catch (Exception e) {
            log.error("info: %s; stack: %s".formatted(e.getMessage(), e.toString()));
        }
    }
}
