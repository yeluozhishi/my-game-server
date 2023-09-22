package com.whk.net.channel;

import com.whk.net.concurrent.GameEventExecutorGroup;
import org.springframework.context.ApplicationContext;

public class GameMessageEventDispatchService {

    /**
     * 业务处理线程池组
     */
    private final GameEventExecutorGroup workerGroup;

    private final GameChannelInitializer channelInitializer;


    public GameMessageEventDispatchService(GameEventExecutorGroup workerGroup, GameChannelInitializer channelInitializer) {
        this.workerGroup = workerGroup;
        this.channelInitializer = channelInitializer;
    }

    public GameEventExecutorGroup getWorkerGroup() {
        return workerGroup;
    }


    public GameChannelInitializer getChannelInitializer() {
        return channelInitializer;
    }
}
