package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import org.whk.protobuf.message.MessageOuterClass;


@GameMessageHandler
public class Handler01 {

    public void message00(MessageOuterClass.Message message){
        System.out.println("Hello World!");
    }

    public void message02(MessageOuterClass.Message message){
        System.out.println("Hello World!");
    }

    public void message03(MessageOuterClass.Message message){
        System.out.println("Hello World!");
    }



}
