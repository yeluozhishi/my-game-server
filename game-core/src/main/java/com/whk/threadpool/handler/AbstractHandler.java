package com.whk.threadpool.handler;

import com.whk.threadpool.IDriver;
import com.whk.threadpool.processor.ProcessorId;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * 句柄
 * 分为自定义数据，执行方法，队列
 */
@Getter
@Setter
public abstract class AbstractHandler implements Runnable{

    protected Logger logger = Logger.getLogger(AbstractHandler.class.getName());

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
            logger.severe("info: %s; stack: %s".formatted(e.getMessage(), Arrays.toString(e.getStackTrace())));
        }
    }
}
