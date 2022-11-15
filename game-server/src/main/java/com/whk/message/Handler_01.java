package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.enity.Message;

@GameMessageHandler
public class Handler_01 {

    public void message_00(Message message){
        System.out.println("Hello World!");
    }

    public void message_02(Message message){
        System.out.println("Hello World!");
    }

    public void message_03(Message message){
        System.out.println("Hello World!");
    }



}
