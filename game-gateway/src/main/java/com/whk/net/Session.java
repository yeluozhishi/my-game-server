package com.whk.net;

import com.whk.protobuf.message.MessageProto;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Session {
    private ChannelHandlerContext handler;
    private long id;
    private int state;


    public Session(ChannelHandlerContext handler) {
        this.handler = handler;
    }

    public void close() {
        handler.close();
    }

    public void sendToClientMessage(MessageProto.Message.Builder msg) {
        handler.writeAndFlush(msg);
    }

    public String getIp() {
        return handler.channel().remoteAddress().toString();
    }

    @Override
    public String toString() {
        return "usrId: %d ip: %s".formatted(id, getIp());
    }
}
