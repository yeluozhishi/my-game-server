package org.whk;

import org.whk.protobuf.message.TipsProto;
import reactor.util.function.Tuple2;

public class TipsConvert {

    public static TipsProto.Tips convert(Tuple2<Integer, String> tuple2){
        return TipsProto.Tips.newBuilder().setCode(tuple2.getT1()).setMsg(tuple2.getT2()).build();
    }
}
