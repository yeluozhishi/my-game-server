package com.whk.net.RPC;

import com.whk.error.GameErrorException;
import com.whk.net.enity.MapBeanServer;
import com.whk.util.MessageI18n;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.Promise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class GameRpcCallbackService {

    private Map<Integer, Promise<MapBeanServer>> callbackMap = new ConcurrentHashMap<>();
    private EventExecutorGroup eventExecutorGroup;
    private int timeout = 30;// 超时时间，30s;


    public GameRpcCallbackService(EventExecutorGroup eventExecutorGroup) {
        this.eventExecutorGroup = eventExecutorGroup;
    }

    public void addCallback(Integer seqId, Promise<MapBeanServer> promise) {
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
        }, timeout, TimeUnit.SECONDS);
    }

    public void callback(MapBeanServer msg) {
        int seqId = msg.getSeqId();
        Promise<MapBeanServer> promise = this.callbackMap.remove(seqId);
        if (promise != null) {
            promise.setSuccess(msg);
        }
    }
}
