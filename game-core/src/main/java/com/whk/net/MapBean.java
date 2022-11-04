package com.whk.net;

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
        if (map != null && !map.isEmpty()){
            super.putAll(map);
        }
    }

    public MapBean(int errCode,String errMsg){
        super.put(CODE_TAG, errCode);
        super.put(ERROR_MSG_TAG, errMsg);
    }

    public static MapBean MapBean(Map map){
        return new MapBean(map);
    }

    public int getInt(String key){
        return (int) get(key);
    }

    public Double getDouble(String key){
        return (Double) get(key);
    }

    public int getDoubleToInt(String key){
        return getDouble(key).intValue();
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