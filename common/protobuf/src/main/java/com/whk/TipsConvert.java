package com.whk;

import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.TipsProto;
import reactor.util.function.Tuple2;

public class TipsConvert {

    public static MessageProto.Message.Builder convert(Tuple2<Integer, String> tuple2){
        return MessageProto.Message.newBuilder().setCommand(5)
                .setTips(TipsProto.Tips.newBuilder().setCode(tuple2.getT1()).setMsg(tuple2.getT2()).build());
    }
}
