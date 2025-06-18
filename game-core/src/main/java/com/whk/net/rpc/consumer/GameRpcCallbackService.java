package com.whk.net.rpc.consumer;

import com.whk.message.MESSAGE_CODE;
import com.whk.message.MessageI18n;
import com.whk.error.GameErrorException;
import com.whk.net.rpc.model.MessageResponse;
import com.whk.threadpool.ThreadPoolManager;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.Promise;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Getter
@Slf4j
public class GameRpcCallbackService {

    private final Map<String, Promise<Object>> callbackMap = new ConcurrentHashMap<>();

    private final DefaultEventExecutorGroup eventExecutors;

    public GameRpcCallbackService() {
        this.eventExecutors = ThreadPoolManager.getInstance().getRpcEventThread();
    }

    /**
     * 超时时间，30s
     */
    private final int TIME_OUT = 30;

    /**
     * 添加回调任务
     *
     * @param seqId   序列
     * @param promise 返回
     */
    public void addCallback(String seqId, Promise<Object> promise) {
        if (promise == null) {
            return;
        }
        callbackMap.put(seqId, promise);
        // 启动一个延时任务，如果到达时间还没有收到返回，超抛出超时异常
        eventExecutors.schedule(() -> {
            Promise<?> value = callbackMap.remove(seqId);
            if (value != null) {
                value.setFailure(new GameErrorException(MESSAGE_CODE.RPC请求超时));
            }
        }, TIME_OUT, TimeUnit.SECONDS);
    }

    public void callback(String seqId, MessageResponse msg) {
        var promise = callbackMap.remove(seqId);
        if (promise != null) {
            if (Objects.nonNull(msg.getError())) log.error(msg.getError());
            promise.setSuccess(msg.getResult()[0]);
        }
    }
}
