package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.client.model.UserMgr;
import com.whk.protobuf.message.*;
import lombok.extern.slf4j.Slf4j;


@GameMessageHandler
@Slf4j
public class Handler00 {
    @HandlerDescription()
    public void message16(PlayerInfoProto.PlayerInfos message, long playerId){
        log.info("body:" + message);
        var user = UserMgr.getUser();
        var m = message.getPlayerInfos(0);
        user.setPlayerId(m.getId());

    }

    @HandlerDescription()
    public void message1(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
        var user = UserMgr.getUser();
        user.setPlayerId(playerId);
    }
    @HandlerDescription()
    public void message2(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
    }
    @HandlerDescription()
    public void message3(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
    }
    @HandlerDescription()
    public void message4(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
    }

    @HandlerDescription()
    public void message17(TipsProto.Tips message, long playerId){
        log.info("code: " + message.getCode()+ ", body:" + message.getMsg());
    }

    @HandlerDescription()
    public void message18(LoginProto.LoginRes message, long playerId){
        log.info(", body:" + message);
    }

    @HandlerDescription()
    public void message19(SceneProto.ResEnterScene message, long playerId){
        log.info(", body:" + message);
    }
}
