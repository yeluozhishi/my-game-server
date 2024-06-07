package com.whk.net;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.internal.ObjectUtil;

import java.util.List;

/**
 * @author wanghongkun
 * @description
 * @date 2023/9/28
 */
public class SelfProtobufDecoder extends MessageToMessageDecoder<ByteBuf> {


    private static final boolean HAS_PARSER;
    private final MessageLite prototype;
    private final ExtensionRegistryLite extensionRegistry;

    public SelfProtobufDecoder(MessageLite prototype) {
        this(prototype, (ExtensionRegistry)null);
    }

    public SelfProtobufDecoder(MessageLite prototype, ExtensionRegistry extensionRegistry) {
        this(prototype, (ExtensionRegistryLite)extensionRegistry);
    }

    public SelfProtobufDecoder(MessageLite prototype, ExtensionRegistryLite extensionRegistry) {
        this.prototype = ((MessageLite) ObjectUtil.checkNotNull(prototype, "prototype")).getDefaultInstanceForType();
        this.extensionRegistry = extensionRegistry;
    }

    protected void decode(ChannelHandlerContext ctx, io.netty.buffer.ByteBuf msg, List<Object> out) throws Exception {
        int length = msg.readableBytes();

        String serverInstance = "1";
        Long playerId = 2L;
        Long userId = 3L;
        int command = 1;

        byte[] array;
        int offset;
        if (msg.hasArray()) {
            array = msg.array();
            offset = msg.arrayOffset() + msg.readerIndex();
        } else {
            array = ByteBufUtil.getBytes(msg, msg.readerIndex(), length, false);
            offset = 0;
        }

        if (this.extensionRegistry == null) {
            if (HAS_PARSER) {
                out.add(this.prototype.getParserForType().parseFrom(array, offset, length));
            } else {
                out.add(this.prototype.newBuilderForType().mergeFrom(array, offset, length).build());
            }
        } else if (HAS_PARSER) {
            out.add(this.prototype.getParserForType().parseFrom(array, offset, length, this.extensionRegistry));
        } else {
            out.add(this.prototype.newBuilderForType().mergeFrom(array, offset, length, this.extensionRegistry).build());
        }

    }

    static {
        boolean hasParser = false;

        try {
            MessageLite.class.getDeclaredMethod("getParserForType");
            hasParser = true;
        } catch (Throwable var2) {
        }

        HAS_PARSER = hasParser;
    }
}
