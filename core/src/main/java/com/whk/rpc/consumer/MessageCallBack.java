
package com.whk.rpc.consumer;

import com.whk.rpc.Constats.RpcSystemConfig;
import com.whk.rpc.model.MessageRequest;
import com.whk.rpc.model.MessageResponse;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 发送消息后的回应回调
 */
public class MessageCallBack {

    private MessageRequest request;
    private MessageResponse response;
    private Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();

    public MessageCallBack(MessageRequest request) {
        this.request = request;
    }

    public Object start() throws InterruptedException {
        if (lock.tryLock()){
            try {
                await();
                if (this.response != null) {
                    boolean isInvokeSucc = getInvokeResult();
                    if (isInvokeSucc) {
                        if (this.response.getError().isEmpty()) {
                            return this.response.getResult()[0];
                        } else {
                            throw new NullPointerException(this.response.getError());
                        }
                    } else {
                        throw new Error(RpcSystemConfig.FILTER_RESPONSE_MSG);
                    }
                } else {
                    return null;
                }
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("获取锁超时");
            return null;
        }

    }

    public void over(MessageResponse response) {
        try {
            lock.lock();
            finish.signal();
            this.response = response;
        } finally {
            lock.unlock();
        }
    }

    private void await() throws InterruptedException {
        boolean timeout = true;
        try {
            timeout = !finish.await(RpcSystemConfig.SYSTEM_PROPERTY_MESSAGE_CALLBACK_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (timeout) {
//            System.out.println(request.getMessageId());
            throw new InterruptedException(RpcSystemConfig.TIMEOUT_RESPONSE_MSG + ". requestInfo:" + request.toString());
        }
    }

    private boolean getInvokeResult() {
        return (!this.response.getError().equals(RpcSystemConfig.FILTER_RESPONSE_MSG) &&
                (!this.response.isReturnNotNull() || (this.response.isReturnNotNull() && this.response.getResult() != null)));
    }
}
