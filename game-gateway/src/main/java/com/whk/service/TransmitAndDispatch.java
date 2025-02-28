package com.whk.service;

import com.whk.CmdToMessageUtil;
import com.whk.dispatchprotocol.DispatchProtocolService;
import com.whk.threadpool.handler.HandlerFactory;
import com.whk.user.UserMgr;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;
import com.whk.protobuf.message.MessageProto;

import java.io.IOException;

@Component
public class TransmitAndDispatch {

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
            var body = CmdToMessageUtil.getInstance().parsePayload(message);
            var isDeal = dispatchProtocolService.dealMessage(message.getCommand(),
                    method -> HandlerFactory.INSTANCE.createPlayerHandler(body, userId, method));
            if (!isDeal) transmit(UserMgr.INSTANCE.wrapperMessage(message, userId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 协议转发
     * 来自客户端，转发给服务器
     *
     * @param message 消息
     */
    private void transmit(MessageProto.Message.Builder message) throws IOException {
        if (message.getPlayerId() == 0L) {
            return;
        }
        UserMgr.INSTANCE.sendToServerMessage(message);
    }

}
