package com.whk.service;

import com.whk.net.Message;
import com.whk.util.SpringUtil;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.IOException;

public enum SendToServerUtil {
    INSTANCE;

    private ServerConnector connector;

    SendToServerUtil(){
        connector = SpringUtil.getAppContext().getBean(ServerConnector.class);
    }

    public void sendMessage(Message message, int serverId){
        try {
            connector.sendToServer(message, serverId).addCallback(new ListenableFutureCallback() {
                @Override
                public void onFailure(Throwable ex) {
                    ex.printStackTrace();
                }

                @Override
                public void onSuccess(Object result) {
                    System.out.println(result.toString());
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
