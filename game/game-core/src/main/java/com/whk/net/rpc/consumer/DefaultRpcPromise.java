package com.whk.net.rpc.consumer;

import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;

public class DefaultRpcPromise extends DefaultPromise<Object> {
    
    public DefaultRpcPromise(EventExecutor executor) {
        super(executor);
    }
}
