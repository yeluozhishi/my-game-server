package com.whk.threadpool.handler;

import java.util.concurrent.ThreadPoolExecutor;

public interface HandlerInterface {

    void doAction(Object... message);

    ThreadPoolExecutor threadPoolExecutor();
}
