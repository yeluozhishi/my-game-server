package com.whk;

import com.whk.protobuf.message.TipsProto;

public class TipsConvert {

    public static TipsProto.Tips convert(int code, String msg){
        return TipsProto.Tips.newBuilder().setCode(code).setMsg(msg).build();
    }
}
