package com.whk.util;

import com.whk.loadconfig.Ipml.ServerMessageConfig;
import com.whk.network_param.MapBean;

public class MessageI18n {

    public static MapBean getMessage(int code){
        var mapBean = new MapBean();
        return mapBean.setErr(code, ServerMessageConfig.getMessage(code));
    }

    public static MapBean getMessage(int code, String... args){
        var mapBean = new MapBean();
        return mapBean.setErr(code, ServerMessageConfig.getMessage(code, args));
    }

}
