package com.whk.threadpool.handler;

import java.util.concurrent.ThreadPoolExecutor;

public interface InstanceHandlerInterface {

    void doAction(Object... message);

    ThreadPoolExecutor threadPoolExecutor();
}
