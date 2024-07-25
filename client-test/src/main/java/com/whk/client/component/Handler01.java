package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.client.model.UserMgr;
import com.whk.protobuf.message.MessageProto;


@GameMessageHandler
public class Handler01 {

    public void message00(MessageProto.Message message, long playerId){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message.getPlayerInfos());
    }

    public void message01(MessageProto.Message message, long playerId){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message);

    }

    public void message02(MessageProto.Message message, long playerId){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message);
    }

    public void message03(MessageProto.Message message, long playerId){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message);
    }

    public void message04(MessageProto.Message message, long playerId){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message);
    }

}
