package com.whk;


import com.whk.loadconfig.Ipml.ServerMessageXMLConfig;
import com.whk.message.MapBean;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

public class MessageI18n {

    /**
     * 消息箱
     * 最好是由展示端封装呈现效果，只用给code和参数列表。
     *
     * @param code 编号
     */
    public static List<ShortMessageBox> getMessageBox(int code){
        var box = new ShortMessageBox();
        box.setCode(code);
        var config = ConfigCacheManager.INSTANCE.getConfigCache(ServerMessageXMLConfig.class);
        box.setMsg(config.getMessage(code));
        return List.of(box);
    }

    public static MapBean getMessage(int code){
        var mapBean = new MapBean();
        var config = ConfigCacheManager.INSTANCE.getConfigCache(ServerMessageXMLConfig.class);
        return mapBean.setErr(code, config.getMessage(code));
    }

    public static MapBean getMessage(int code, String... args){
        var mapBean = new MapBean();
        var config = ConfigCacheManager.INSTANCE.getConfigCache(ServerMessageXMLConfig.class);
        return mapBean.setErr(code, config.getMessage(code, args));
    }

    public static Tuple2<Integer, String> getMessageTuple(int code){
        var config = ConfigCacheManager.INSTANCE.getConfigCache(ServerMessageXMLConfig.class);
        return Tuples.of(code, config.getMessage(code));
    }

    public static Tuple2<Integer, String> getMessageTuple(int code, String... args){
        var config = ConfigCacheManager.INSTANCE.getConfigCache(ServerMessageXMLConfig.class);
        return Tuples.of(code, config.getMessage(code, args));
    }



}
