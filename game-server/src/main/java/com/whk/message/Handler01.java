package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.ThreadAssign;
import com.whk.threadpool.ThreadPoolManager;
import org.whk.protobuf.message.MessageOuterClass;


@GameMessageHandler
public class Handler01 {

    @ThreadAssign
    public void message00(MessageOuterClass.Message message){
        System.out.println("Hello World 1!");
    }
    @ThreadAssign(ThreadPoolManager.RPC_THREAD)
    public void message02(MessageOuterClass.Message message){
        System.out.println("Hello World 2!");
    }

    public void message03(MessageOuterClass.Message message){
        System.out.println("Hello World 3!");
    }



}
