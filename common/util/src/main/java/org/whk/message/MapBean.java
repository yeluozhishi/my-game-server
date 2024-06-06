package org.whk.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapBean extends HashMap<String, Object> {

    /** 状态码 */
    public static final String CODE_TAG = "code";

    public static final int DEFAULT_CODE = 100;

    /** 返回内容 */
    public static final String ERROR_MSG_TAG = "err_msg";

    public MapBean(){

    }

    public MapBean(Map<? extends java.lang.String,?> map){
        if (map != null && !map.isEmpty()){
            super.putAll(map);
        }
    }

    public MapBean(int errCode,String errMsg){
        super.put(CODE_TAG, errCode);
        super.put(ERROR_MSG_TAG, errMsg);
    }

    public static MapBean mapBean(Map<? extends java.lang.String,?> map){
        return new MapBean(map);
    }

    public static MapBean failure(String err_msg){
        var bean = new MapBean();
        bean.put(CODE_TAG, DEFAULT_CODE);
        bean.put(ERROR_MSG_TAG, err_msg);
        return bean;
    }

    public MapBean setErr(int code, String msg){
        super.put(CODE_TAG, code);
        super.put(ERROR_MSG_TAG, msg);
        return this;
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

    public <T> List<T> getList(String key){
        return (List<T>) get(key);
    }

    public Long getLong(String key) {
        return Long.parseLong(getString(key));
    }

    public Long getLong(String key, long defaultValue) {
        return (Long) getOrDefault(key, defaultValue);
    }

    public List<Long> getListForLong(String key) {
        return getList(key);
    }


}