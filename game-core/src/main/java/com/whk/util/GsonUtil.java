package com.whk.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum GsonUtil{
    INSTANCE;

    private final Gson gson;

    GsonUtil() {
        gson = new Gson();
    }


    /**
     * 将object对象转成json字符串
     *
     * @param object 对象
     * @return String
     */
    public String GsonString(Object object) {
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
    public <T> T GsonToBean(String gsonString, Class<T> cls) {
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
    public <T> List<T> GsonToList(String gsonString) {
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
        Gson gson = new Gson();
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
    public <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
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
    public <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map;
        map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
        }.getType());
        return map;
    }

}
