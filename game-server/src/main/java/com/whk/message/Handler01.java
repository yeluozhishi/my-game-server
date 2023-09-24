package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import org.whk.message.Message;

@GameMessageHandler
public class Handler01 {

    public void message00(Message message){
        System.out.println("Hello World!");
    }

    public void message02(Message message){
        System.out.println("Hello World!");
    }

    public void message03(Message message){
        System.out.println("Hello World!");
    }



}
