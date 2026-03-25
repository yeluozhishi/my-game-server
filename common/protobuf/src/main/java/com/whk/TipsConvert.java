package com.whk;

import com.google.protobuf.ByteString;
import com.whk.protobuf.message.TipsProto;
import reactor.util.function.Tuple2;

public class TipsConvert {

    public static ByteString convert(int code, String msg){
        return TipsProto.Tips.newBuilder().setCode(code).setMsg(msg).build().toByteString();
    }
}
