package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.enity.Message;

@GameMessageHandler
public class Handler00 {

    public void message00(Message message){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message.getBody());
    }

    public void message01(Message message){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message.getBody());
    }

}
