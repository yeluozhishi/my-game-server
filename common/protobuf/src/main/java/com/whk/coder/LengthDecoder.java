package com.whk.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class LengthDecoder extends LengthFieldBasedFrameDecoder {

    public LengthDecoder() {
        super(1048576, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }

        try {
            return frame.retainedDuplicate();
        } finally {
            frame.release();
        }
    }
}
