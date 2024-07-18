package com.whk.net.rpc.serialize;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.buffer.ByteBuf;
import com.whk.protobuf.message.MessageProto;

import java.io.IOException;

public interface MessageCodecUtil {
    int MESSAGE_LENGTH = 4;
}
