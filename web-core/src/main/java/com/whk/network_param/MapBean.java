package com.whk.network_param;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapBean extends HashMap<String, Object> {

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String ERROR_MSG_TAG = "err_msg";

    public MapBean(){

    }

    /**
     * 返回错误信息
     * @param error 错误信息
     */
    public MapBean(IServerError error){
        super.put(CODE_TAG, error.getErrorCode());
        super.put(ERROR_MSG_TAG, error.getErrorDesc());
    }

    /**
     * 返回错误信息
     * @param error 错误信息
     */
    public MapBean(IServerError error, String msg){
        super.put(CODE_TAG, error.getErrorCode());
        super.put(ERROR_MSG_TAG, msg);
    }

    public MapBean(Map map){
        if (map != null && !map.isEmpty()){
            super.putAll(map);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static MapBean success()
    {
        return new MapBean(WebCenterError.SUCCESS);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static MapBean success(String msg)
    {
        return new MapBean(WebCenterError.SUCCESS, msg);
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
