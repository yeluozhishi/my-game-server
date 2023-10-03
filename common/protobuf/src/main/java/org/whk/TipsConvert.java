package org.whk;

import org.whk.protobuf.message.TipsOuterClass;
import reactor.util.function.Tuple2;

public class TipsConvert {

    public static TipsOuterClass.Tips convert(Tuple2<Integer, String> tuple2){
        return TipsOuterClass.Tips.newBuilder().setCode(tuple2.getT1()).setMsg(tuple2.getT2()).build();
    }
}
