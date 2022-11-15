package com.whk.util;


import com.whk.loadconfig.Ipml.ServerMessageConfig;
import com.whk.net.enity.MapBean;
import reactor.function.TupleUtils;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

public class MessageI18n {

    public static MapBean getMessage(int code){
        var mapBean = new MapBean();
        return mapBean.setErr(code, ServerMessageConfig.getMessage(code));
    }

    public static MapBean getMessage(int code, String... args){
        var mapBean = new MapBean();
        return mapBean.setErr(code, ServerMessageConfig.getMessage(code, args));
    }

    public static Tuple2<Integer, String> getMessageTuple(int code){
        return Tuples.of(code, ServerMessageConfig.getMessage(code));
    }

    public static Tuple2<Integer, String> getMessageTuple(int code, String... args){
        return Tuples.of(code, ServerMessageConfig.getMessage(code, args));
    }



}
