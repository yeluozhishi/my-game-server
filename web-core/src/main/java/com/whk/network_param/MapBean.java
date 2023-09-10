package com.whk.network_param;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Administrator
 */
public class MapBean extends HashMap<String, Object> {

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";

    /**
     * 返回内容
     */
    public static final String ERROR_MSG_TAG = "err_msg";

    public MapBean() {

    }

    public MapBean(Map<? extends java.lang.String,?> map) {
        super.putAll(map);
    }

    public MapBean setErr(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(ERROR_MSG_TAG, msg);
        return this;
    }

    public int getInt(String key) {
        return (Integer) get(key);
    }

    public int getInt(String key, int defaultValue) {
        return (Integer) getOrDefault(key, defaultValue);
    }

    public String getString(String key) {
        return (String) get(key);
    }

    public String getString(String key, String defaultValue) {
        return (String) getOrDefault(key, defaultValue);
    }

    public LocalDateTime getLocalDateTime(String key, DateTimeFormatter formatter) {
        return LocalDateTime.parse(getString(key), formatter);
    }

    public List<Integer> getListForInteger(String key){
        return getList(key, Integer.class);
    }

    public List<Long> getListForLong(String key){
        return getList(key, Long.class);
    }

    public List<String> getListForString(String key){
        return getList(key, String.class);
    }

    private  <T> List<T> getList(String key, Class<T> cla) {
        var obj = get(key);
        List<T> list = new LinkedList<>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                list.add(cla.cast(o));
            }
        }
        return list;
    }


    public Long getLong(String key, long defaultValue) {
        return Long.parseLong(getOrDefault(key, defaultValue).toString());
    }
}
