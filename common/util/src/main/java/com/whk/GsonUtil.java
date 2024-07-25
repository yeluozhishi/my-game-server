package com.whk;

import com.google.gson.*;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.whk.GsonAdapter.DateAdapter;
import com.whk.GsonAdapter.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.*;

/**
 *
 * 类型偏差？ long or int 识别成double
 */
public enum GsonUtil{
    INSTANCE;

    private final Gson gson;

    GsonUtil() {
        gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .registerTypeAdapter(Date.class, new DateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }


    /**
     * 将object对象转成json字符串
     *
     * @param object 对象
     * @return String
     */
    public String gsonString(Object object) {
        String gsonString;
        gsonString = gson.toJson(object);
        return gsonString;
    }


    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString 字符串
     * @param cls 类
     * @return T
     */
    public <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t;
        t = gson.fromJson(gsonString, cls);
        return t;
    }


    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString 字符串
     * @return List<T>
     */
    public <T> List<T> gsonToList(String gsonString) {
        List<T> list;
        list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
        }.getType());
        return list;
    }


    /**
     * 转成list
     * 解决泛型问题
     * @param json json串
     * @param cls 类
     * @return List<T>
     */
    public <T> List<T> jsonToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<>();
        JsonArray array = JsonParser.parseString(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }



    /**
     * 转成list中有map的
     *
     * @param gsonString 字符串
     * @return List<Map<String, T>>
     */
    public <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list;
        list = gson.fromJson(gsonString,
                new TypeToken<List<Map<String, T>>>() {
                }.getType());
        return list;
    }


    /**
     * 转成map的
     *
     * @param gsonString 字符串
     * @return Map<String, T>
     */
    public <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map;
        map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }

    /**
     * 转成map的
     *
     * @param gsonString 字符串
     * @return Map<String, T>
     */
    public Map<String, Object> gsonToMapsObject(String gsonString) {
        return gson.fromJson(gsonString, new TypeToken<Map<String,Object>>() {}.getType());
    }

    /**
     * 转成map的
     *
     * @param gsonString 字符串
     * @return Map<String, Long>
     */
    public Map<String, Long> gsonToMapsLong(String gsonString) {
        return gson.fromJson(gsonString, new TypeToken<Map<String, Long>>() {
        }.getType());
    }

}
