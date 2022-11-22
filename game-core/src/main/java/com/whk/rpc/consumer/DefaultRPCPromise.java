package com.whk.rpc.consumer;

import com.whk.rpc.model.MessageResponse;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;

public class DefaultRPCPromise extends DefaultPromise<MessageResponse> {
    
    public DefaultRPCPromise(EventExecutor executor) {
        super(executor);
    }
}
