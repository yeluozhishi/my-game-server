package com.whk.threadpool.processor;

import com.whk.threadpool.ThreadType;
import com.whk.threadpool.driver.IDriver;
import com.whk.threadpool.driver.QueueDriver;
import com.whk.threadpool.handler.RPCMessageHandler;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class RPCProcessor extends AbstractMessageProcessor<RPCMessageHandler> {

    @Override
    public ThreadType getThreadType() {
        return ThreadType.RPC_THREAD;
    }

    protected IDriver addDriver(String id, ThreadPoolExecutor executor) {
        IDriver IDriver = new QueueDriver(executor, "RPC驱动器%s".formatted(id), new ConcurrentLinkedQueue<>());
        getDriverMap().putIfAbsent(id, IDriver);
        return IDriver;
    }

}
