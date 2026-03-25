package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.client.model.UserMgr;
import com.whk.protobuf.message.*;
import lombok.extern.slf4j.Slf4j;


@GameMessageHandler
@Slf4j
public class Handler00 {
    @HandlerDescription(number = 0)
    public void message00(PlayerInfoProto.PlayerInfos message, long playerId){
        log.info("body:" + message);
        var user = UserMgr.getUser();
        var m = message.getPlayerInfos(0);
        user.setPlayerId(m.getId());

    }

    @HandlerDescription(number = 1)
    public void message01(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
        var user = UserMgr.getUser();
        user.setPlayerId(playerId);
    }
    @HandlerDescription(number = 2)
    public void message02(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
    }
    @HandlerDescription(number = 3)
    public void message03(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
    }
    @HandlerDescription(number = 4)
    public void message04(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
    }
    @HandlerDescription(number = 5)
    public void message05(TipsProto.Tips message, long playerId){
        log.info("body:" + message);
    }

    @HandlerDescription(number = 6)
    public void message06(PlayerInfoProto.PlayerInfos message, long playerId){
        log.info(", body:" + message);
    }

    @HandlerDescription(number = 7)
    public void message07(TipsProto.Tips message, long playerId){
        log.info("code: " + message.getCode()+ ", body:" + message.getMsg());
    }

    @HandlerDescription(number = 8)
    public void message08(LoginProto.LoginRes message, long playerId){
        log.info(", body:" + message);
    }

    @HandlerDescription(number = 9)
    public void message09(SceneProto.ResEnterScene message, long playerId){
        log.info(", body:" + message);
    }
}
