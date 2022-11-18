package com.whk.net.channel;

import com.whk.net.RPC.GameRpcService;
import com.whk.net.concurrent.GameEventExecutorGroup;
import org.springframework.context.ApplicationContext;

public class GameMessageEventDispatchService {

    /**
     * 业务处理线程池组
     */
    private final GameEventExecutorGroup workerGroup;

    private final GameChannelInitializer channelInitializer;

    private final GameRpcService rpcService;

    private ApplicationContext context;

    public GameMessageEventDispatchService(GameEventExecutorGroup workerGroup, GameChannelInitializer channelInitializer,
                                           GameRpcService rpcService, ApplicationContext context) {
        this.workerGroup = workerGroup;
        this.channelInitializer = channelInitializer;
        this.rpcService = rpcService;
        this.context = context;
    }

    public GameEventExecutorGroup getWorkerGroup() {
        return workerGroup;
    }

    public GameRpcService getRpcService() {
        return rpcService;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
