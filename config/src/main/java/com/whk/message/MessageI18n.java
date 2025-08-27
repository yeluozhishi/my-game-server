package com.whk.message;


import com.whk.ConfigCacheManager;
import com.whk.config.ServerMessageConfig;

public class MessageI18n {

    /**
     * 消息箱
     * 最好是由展示端封装呈现效果，只用给code和参数列表。
     *
     * @param code 编号
     */
    public static ShortMessageBox getMessageBox(int code, String... args){
        var box = new ShortMessageBox();
        box.setCode(code);
        box.setMsg(args);
        return box;
    }

    public static MapBean getMessageMapBean(MESSAGE_CODE code){
        var config = ConfigCacheManager.INSTANCE.getConfigCache(ServerMessageConfig.class);
        return new MapBean().setErr(code.getCode(), config.getMessage(code.getCode()));
    }

    public static MapBean getMessageMapBean(MESSAGE_CODE code, String... args){
        var config = ConfigCacheManager.INSTANCE.getConfigCache(ServerMessageConfig.class);
        return new MapBean().setErr(code.getCode(), config.getMessage(code.getCode(), args));
    }

    public static String getMessage(MESSAGE_CODE code){
        var config = ConfigCacheManager.INSTANCE.getConfigCache(ServerMessageConfig.class);
        return config.getMessage(code.getCode());
    }

    public static String getMessage(MESSAGE_CODE code, String... args){
        var config = ConfigCacheManager.INSTANCE.getConfigCache(ServerMessageConfig.class);
        return config.getMessage(code.getCode(), args);
    }

}
