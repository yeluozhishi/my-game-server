package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.protobuf.message.MessageProto;


@GameMessageHandler
public class Handler00 {

    public void message00(MessageProto.Message message, long playerId){

    }

    public void message03(MessageProto.Message message, long playerId){

    }


    /**
     * tips
     *
     * @param message
     */
    public void message05(MessageProto.Message message, long playerId) {
    }
}
