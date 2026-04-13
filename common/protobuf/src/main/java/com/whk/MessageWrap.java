package com.whk;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public record MessageWrap(int cmd, ByteString body) {

    public Message decode() throws InvalidProtocolBufferException {
        Message message = CmdToMessageUtil.getInstance().getMessageClass(cmd());
        if (message == null) {
            log.info("未找到消息： %d".formatted(cmd()));
            return null;
        }

        return message.getParserForType().parseFrom(body());
    }
}
