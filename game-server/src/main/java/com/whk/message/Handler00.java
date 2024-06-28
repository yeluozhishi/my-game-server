package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import org.whk.protobuf.message.MessageProto;


@GameMessageHandler
public class Handler00 {

    public void message00(MessageProto.Message message){

    }


    /**
     * tips
     *
     * @param message
     */
    public void message05(MessageProto.Message message, long userId) {
    }
}
