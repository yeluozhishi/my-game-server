package com.whk.threadpool.processor;

import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.driver.QueueDriver;
import com.whk.threadpool.handler.RPCMessageHandler;

import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class RPCProcessor extends AbstractMessageProcessor<RPCMessageHandler> {

    protected IDriver addDriver(String id, ThreadPoolExecutor executor) {
        IDriver IDriver = new QueueDriver(executor, "RPC驱动器%s".formatted(id), new ConcurrentLinkedQueue<>());
        getDriverMap().putIfAbsent(id, IDriver);
        return IDriver;
    }

    @Override
    public void message0(RPCMessageHandler handler) {
        IDriver driver = getDriverMap().get(handler.getOrderId());
        if (Objects.isNull(driver)) {
            driver = addDriver(handler.getOrderId(), ThreadPoolManager.getInstance().getExecutor(ThreadType.RPC_THREAD));
        }
        driver.addEvent(handler);
    }

}
