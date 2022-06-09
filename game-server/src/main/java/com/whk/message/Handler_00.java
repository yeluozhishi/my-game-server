package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.Message;

@GameMessageHandler
public class Handler_00 {

    public void message_00(Message message){
        System.out.println("get message_00");

    }

    public void message_01(Message message){

        System.out.println("get message_01");

    }


}
