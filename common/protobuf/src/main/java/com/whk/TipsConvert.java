package com.whk;

import com.google.protobuf.ByteString;
import com.whk.protobuf.message.TipsProto;
import reactor.util.function.Tuple2;

public class TipsConvert {

    public static ByteString convert(Tuple2<Integer, String> tuple2){
        return TipsProto.Tips.newBuilder().setCode(tuple2.getT1()).setMsg(tuple2.getT2()).build().toByteString();
    }
}
