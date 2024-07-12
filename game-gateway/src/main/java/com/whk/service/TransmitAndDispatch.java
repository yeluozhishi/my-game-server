package com.whk.service;

import com.whk.threadpool.dispatchprotocol.DispatchProtocolService;
import com.whk.threadpool.event.EventFactory;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.MessageWrapperProto;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class TransmitAndDispatch {

    private final Logger logger = Logger.getLogger(TransmitAndDispatch.class.getName());

    private DispatchProtocolService dispatchProtocolService;


    public void init() {
        dispatchProtocolService = new DispatchProtocolService();
    }


    /**
     * 消费客户端消息
     */
    public void consumerClientMessage(MessageProto.Message message, ChannelHandlerContext ctx) {

        long userId = Long.parseLong(ctx.channel().attr(UserMgr.INSTANCE.ATTR_USER_ID).get().toString());

        try {
            dispatchProtocolService.dealMessage(message, userId,
                    method -> EventFactory.INSTANCE.createUserEvent(message, userId, method));
            transmit(UserMgr.INSTANCE.WrapperMessage(message, userId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 协议转发
     * 来自客户端，转发给服务器
     * @param message 消息
     */
    private void transmit(MessageWrapperProto.MessageWrapper message) throws IOException {
        if (message.getPlayerId() == 0L) {
            return;
        }
        UserMgr.INSTANCE.sendToServerMessage(message);
    }

}