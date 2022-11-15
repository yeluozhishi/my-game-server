package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.enity.Message;

@GameMessageHandler
public class Handler_00 {

    public void message_00(Message message){
        System.out.println("get message_00" + message.getBody());

    }

}
