package com.whk.net.channel;

import com.whk.net.RPC.GameRpcService;
import com.whk.net.concurrent.GameEventExecutorGroup;
import io.netty.util.concurrent.EventExecutor;
import org.springframework.context.ApplicationContext;

public class GameMessageEventDispatchService {

    /**
     * 业务处理线程池组
     */
    private GameEventExecutorGroup workerGroup;
    /**
     * 当前管理gameChannelGroup集合的事件线程池
     */
    private EventExecutor executor;


    private GameChannelInitializer channelInitializer;

    private GameRpcService rpcSendFactory;

    private ApplicationContext context;

    public GameMessageEventDispatchService(GameEventExecutorGroup workerGroup, GameChannelInitializer channelInitializer,
                                           GameRpcService rpcSendFactory, ApplicationContext context) {
        this.workerGroup = workerGroup;
        this.executor = workerGroup.next();
        this.channelInitializer = channelInitializer;
        this.rpcSendFactory = rpcSendFactory;
        this.context = context;
    }

    public GameEventExecutorGroup getWorkerGroup() {
        return workerGroup;
    }

    public void setWorkerGroup(GameEventExecutorGroup workerGroup) {
        this.workerGroup = workerGroup;
    }

    public EventExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(EventExecutor executor) {
        this.executor = executor;
    }

    public GameChannelInitializer getChannelInitializer() {
        return channelInitializer;
    }

    public void setChannelInitializer(GameChannelInitializer channelInitializer) {
        this.channelInitializer = channelInitializer;
    }

    public GameRpcService getRpcSendFactory() {
        return rpcSendFactory;
    }

    public void setRpcSendFactory(GameRpcService rpcSendFactory) {
        this.rpcSendFactory = rpcSendFactory;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
