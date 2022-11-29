package com.whk.net.RPC;

import com.whk.error.GameErrorException;
import com.whk.rpc.model.MessageResponse;
import com.whk.util.MessageI18n;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class GameRpcCallbackService {

    private final Map<String, Promise<Object>> callbackMap = new ConcurrentHashMap<>();

    private final EventExecutorGroup eventExecutorGroup;

    /**
     * 超时时间，30s
     */
    private final int TIME_OUT = 30;


    public GameRpcCallbackService(EventExecutorGroup eventExecutorGroup) {
        this.eventExecutorGroup = eventExecutorGroup;
    }

    public void addCallback(String seqId, Promise<Object> promise) {
        if(promise == null) {
            return ;
        }
        callbackMap.put(seqId, promise);
        // 启动一个延时任务，如果到达时间还没有收到返回，超抛出超时异常
        eventExecutorGroup.schedule(() -> {
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
