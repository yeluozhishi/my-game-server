package com.whk.network_param;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class MapBean extends HashMap<String, Object> {

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String ERROR_MSG_TAG = "err_msg";

    public MapBean(){

    }

    public MapBean(Map map){
        super.putAll(map);
    }

    public MapBean setErr(int code, String msg){
        super.put(CODE_TAG, code);
        super.put(ERROR_MSG_TAG, msg);
        return this;
    }

    public int getInt(String key){
        return (Integer) get(key);
    }

    public int getInt(String key, int defaultValue){
        return (Integer) getOrDefault(key, defaultValue);
    }

    public String getString(String key){
        return (String) get(key);
    }

    public String getString(String key, String defaultValue){
        return (String) getOrDefault(key, defaultValue);
    }

    public LocalDateTime getLocalDateTime(String key, DateTimeFormatter formatter){
        return LocalDateTime.parse(getString(key), formatter);
    }



}
