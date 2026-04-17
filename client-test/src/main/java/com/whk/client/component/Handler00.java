package com.whk.client.component;

import com.google.protobuf.Message;
import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.client.model.UserMgr;
import com.whk.protobuf.message.*;
import lombok.extern.slf4j.Slf4j;


@GameMessageHandler
@Slf4j
public class Handler00 {
    @HandlerDescription()
    public void message16(PlayerInfoProto.PlayerInfos message, long userId){
        var user = UserMgr.getInstance().getUser(userId);
        var m = message.getPlayerInfos(0);
        user.setPlayerId(m.getId());
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
    public void message14(Message message, long playerId){
        log.info("body:" + message);
    }

    @HandlerDescription()
    public void message17(TipsProto.Tips message, long playerId){
        log.info("code: " + message.getCode()+ ", body:" + message.getMsg());
    }

    @HandlerDescription()
    public void message18(LoginProto.ResLogin message, long playerId){
        log.info(", body:" + message);
    }

    @HandlerDescription()
    public void message19(SceneProto.ResEnterScene message, long playerId){
        log.info(", body:" + message);
    }

    @HandlerDescription()
    public void message20(CreatePlayerProto.ResCreatePlayer message, long playerId){
        var user = UserMgr.getInstance().getUser(message.getPlayerInfo().getUserId());
        user.setPlayerId(playerId);
    }

    @HandlerDescription()
    public void message100(Message message, long playerId){
        log.info("body:" + message);
    }

    @HandlerDescription()
    public void message101(Message message, long playerId){
        log.info("body:" + message);
    }

    @HandlerDescription()
    public void message102(Message message, long playerId){
        log.info("body:" + message);
    }

    @HandlerDescription()
    public void message103(Message message, long playerId){
        log.info("body:" + message);
    }
}
