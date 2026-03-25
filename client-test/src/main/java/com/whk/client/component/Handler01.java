package com.whk.client.component;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.PlayerInfoProto;
import lombok.extern.slf4j.Slf4j;


@GameMessageHandler
@Slf4j
public class Handler01 {
    @HandlerDescription(number = 100)
    public void message00(PlayerInfoProto.PlayerInfos message, long playerId){
        log.info(", body:" + message);
    }
    @HandlerDescription(number = 101)
    public void message01(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);

    }
    @HandlerDescription(number = 102)
    public void message02(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
    }
    @HandlerDescription(number = 103)
    public void message03(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
    }
    @HandlerDescription(number = 104)
    public void message04(MessageProto.Message message, long playerId){
        log.info("get Command:" + message.getCommand() + ", body:" + message);
    }

}
