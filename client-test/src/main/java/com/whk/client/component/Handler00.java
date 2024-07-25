package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.client.model.UserMgr;
import com.whk.message.MapBean;
import com.whk.protobuf.message.MessageProto;


@GameMessageHandler
public class Handler00 {

    public void message00(MessageProto.Message message, long playerId){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message.getPlayerInfos());
        var user = UserMgr.getUser();
        var l = message.getPlayerInfos();
        var m = l.getPlayerInfos(0);
        user.setPlayerId(m.getId());

    }

    public void message01(MessageProto.Message message, long playerId){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message);
        var user = UserMgr.getUser();
        user.setPlayerId(playerId);
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

    public void message05(MessageProto.Message message, long playerId){
        System.out.println("get Command:" + message.getCommand() + ", body:" + message);
        System.out.println(message.getTips().getMsg());
    }

}
