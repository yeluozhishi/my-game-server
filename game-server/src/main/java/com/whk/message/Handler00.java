package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.protobuf.message.MessageProto;


@GameMessageHandler
public class Handler00 {

    @HandlerDescription(number = "0005", desc = "tips")
    public void message05(MessageProto.Message message, long playerId) {
    }
}
