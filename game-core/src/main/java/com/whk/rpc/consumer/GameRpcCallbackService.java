package com.whk.rpc.consumer;

import com.whk.MessageI18n;
import com.whk.error.GameErrorException;
import com.whk.rpc.model.MessageResponse;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Promise;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Getter
public class GameRpcCallbackService {

    private final Map<String, Promise<Object>> callbackMap = new ConcurrentHashMap<>();

    private final DefaultEventExecutorGroup eventExecutors;

    /**
     * 超时时间，30s
     */
    private final int TIME_OUT = 30;


    public GameRpcCallbackService(DefaultEventExecutorGroup eventExecutors) {
        this.eventExecutors = eventExecutors;
    }

    public void addCallback(String seqId, Promise<Object> promise) {
        if(promise == null) {
            return ;
        }
        callbackMap.put(seqId, promise);
        // 启动一个延时任务，如果到达时间还没有收到返回，超抛出超时异常
        eventExecutors.schedule(() -> {
            Promise<?> value = callbackMap.remove(seqId);
            if (value != null) {
                value.setFailure(new GameErrorException(MessageI18n.getMessageTuple(9)));
            }
        }, TIME_OUT, TimeUnit.SECONDS);
    }

    public void callback(String seqId, MessageResponse msg) {
        var promise = callbackMap.remove(seqId);
        if (promise != null) {
            promise.setSuccess(msg.getResult()[0]);
        }
    }
}
