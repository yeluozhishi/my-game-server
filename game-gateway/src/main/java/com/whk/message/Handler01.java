package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import org.whk.protobuf.message.MessageProto;


@GameMessageHandler
public class Handler01 {

    public void message00(MessageProto.Message message){
        System.out.println("Hello World!");
    }

    public void message02(MessageProto.Message message){
        System.out.println("Hello World!");
    }

    public void message03(MessageProto.Message message){
        System.out.println("Hello World!");
    }



}
