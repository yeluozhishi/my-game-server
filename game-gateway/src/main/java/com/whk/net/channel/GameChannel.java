package com.whk.net.channel;

import com.whk.user.User;
import io.netty.util.concurrent.EventExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import com.whk.protobuf.message.MessageWrapperProto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 绑定playerId的channel
 * 用于服务器之间的玩家信息收发处理
 */
public class GameChannel {

    private final Logger logger = Logger.getLogger(GameChannel.class.getName());

    /**
     * 服务器实例id
     */
    private String instanceId;

    /**
     * 游戏服id
     */
    private int serverId;

    /**
     * 跳转游戏服id
     */
    private int toServerId;

    /**
     * 绑定的线程
     */
    private volatile EventExecutor executor;

    /**
     * 处理事件的链表
     */
    private GameChannelPipeline pipeline;

    private KafkaTemplate<String, byte[]> kafkaTemplate;

    private User user;

    /**
     * 事件等待队列，如果GameChannel还没有注册成功，这个时候又有新的消息过来了，就让事件在这个队列中等待。
     */
    private List<Runnable> waitTaskList = new ArrayList<>(5);

    private boolean registered;

    public void init(String instanceId, int serverId, int toServerId, EventExecutor executor,
                     GameChannelInitializer initializer, KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.instanceId = instanceId;
        this.serverId = serverId;
        this.toServerId = toServerId;
        this.executor = executor;
        this.pipeline = new GameChannelPipeline(this);
        this.kafkaTemplate = kafkaTemplate;
        initializer.initChannel(this);
    }

    public void sendToServerMessage(MessageWrapperProto.MessageWrapper msg) throws IOException {
//        GameMessageInnerDecoder.INSTANCE.sendMessage(kafkaTemplate, msg);
    }

    public EventExecutor executor() {
        return executor;
    }

    public void unsafeClose() {
        fireChannelInactive();
    }

    public void register(Long playerId, User user) {
        this.user = user;
        GameChannelPromise promise = new DefaultGameChannelPromise(this, executor);
        pipeline.fireChannelRegistered(playerId, promise);
        promise.addListener(future -> {
            if (future.isSuccess()) {
                // 注册成功的时候，设置为true
                registered = true;
                // 注册channel成功之后，执行等待的任务，因为此执行这些任务和判断是否注册完成是在同一个线程中，所以此处执行完之后，waitTaskList中不会再有新的任务了。
                waitTaskList.forEach(Runnable::run);
            } else {
                fireChannelInactive();
                logger.warning("player {} channel 注册失败" + playerId + ": " + future.cause());
            }
        });
    }

    public void fireChannelInactive() {
        this.safeExecute(() -> {
            pipeline.fireChannelInactive();
        });
    }

    private void safeExecute(Runnable task) {
        if (this.executor.inEventLoop()) {
            this.safeExecute0(task);
        } else {
            this.executor.execute(() -> this.safeExecute0(task));
        }
    }

    /**
     * 玩家消息专用
     * @param msg 消息
     */
    public void fireReadGameMessage(MessageWrapperProto.MessageWrapper msg) {
        this.safeExecute(() -> pipeline.fireChannelRead(msg));
    }

    private void safeExecute0(Runnable task) {
        try {
            if (!this.registered) {
                waitTaskList.add(task);
            } else {
                task.run();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public GameChannelPipeline getPipeline() {
        return pipeline;
    }

    public boolean isRegistered() {
        return registered;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getToServerId() {
        return toServerId;
    }

    public void setToServerId(int toServerId) {
        this.toServerId = toServerId;
    }
}
