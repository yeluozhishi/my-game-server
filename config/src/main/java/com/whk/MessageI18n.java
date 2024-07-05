package com.whk;


import com.whk.loadconfig.Ipml.ServerMessageConfig;
import com.whk.message.MapBean;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

public class MessageI18n {

    /**
     * 消息箱
     * 最好是由展示端封装呈现效果，只用给code和参数列表。
     *
     * @param code
     * @return
     */
    public static List<ShortMessageBox> getMessageBox(int code){
        var box = new ShortMessageBox();
        box.setCode(code);
        box.setMsg(ServerMessageConfig.getMessage(code));
        return List.of(box);
    }

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
