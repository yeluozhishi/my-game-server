package com.whk.threadpool.processor;

import com.whk.threadpool.IDriver;
import com.whk.threadpool.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.handler.AbstractHandler;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RPCMessageProcessor extends AbstractMessageProcessor{

    private IDriver driver;

    public RPCMessageProcessor() {
        driver = new QueueDriver(ThreadPoolManager.getInstance().getExecutor(ThreadType.RPC_THREAD), "DB驱动器", new ConcurrentLinkedQueue<>());
    }

    @Override
    public void message(AbstractHandler abstractHandler) {
        driver.addEvent(abstractHandler);
    }
}
