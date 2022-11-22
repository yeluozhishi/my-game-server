package com.whk.net.channel;

import com.whk.net.concurrent.GameEventExecutorGroup;
import org.springframework.context.ApplicationContext;

public class GameMessageEventDispatchService {

    /**
     * 业务处理线程池组
     */
    private final GameEventExecutorGroup workerGroup;

    private final GameChannelInitializer channelInitializer;

    private ApplicationContext context;

    public GameMessageEventDispatchService(GameEventExecutorGroup workerGroup, GameChannelInitializer channelInitializer,
                                           ApplicationContext context) {
        this.workerGroup = workerGroup;
        this.channelInitializer = channelInitializer;
        this.context = context;
    }

    public GameEventExecutorGroup getWorkerGroup() {
        return workerGroup;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public GameChannelInitializer getChannelInitializer() {
        return channelInitializer;
    }
}
