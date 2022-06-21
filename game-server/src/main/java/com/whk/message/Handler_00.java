package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.Message;
import com.whk.messageholder.SendMessageHolder;

import java.util.Map;

@GameMessageHandler
public class Handler_00 {

    public void message_00(Message message){
        System.out.println("get message_00" + message.getBody());

    }

    public void message_01(Message message){

        System.out.println("get message_01:" + message.getBody());

        message.setBody(Map.of("msg", "i got it"));

        // send back
        SendMessageHolder.INSTANCE.sendMessage(message);


    }


}
