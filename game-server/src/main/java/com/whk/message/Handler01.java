package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.ThreadAssign;
import com.whk.threadpool.TheadType;
import com.whk.protobuf.message.MessageProto;


@GameMessageHandler
public class Handler01 {

    @ThreadAssign
    public void message00(MessageProto.Message message, long playerId){
        System.out.println("Hello World 1!");
    }
    @ThreadAssign(TheadType.SCENE_THREAD)
    public void message02(MessageProto.Message message, long playerId){
        System.out.println("Hello World 2!");
    }

    public void message03(MessageProto.Message message, long playerId){
        System.out.println("Hello World 3!");
    }



}
