package org.whk;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * JSON处理工具类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {

    private static ObjectMapper OBJECT_MAPPER = SpringUtils.getBean(ObjectMapper.class);

    /**
     * 设置日期格式
     *
     * @param object 传入的待转换格式的日期对象
     * @return json字符串
     */
    public static String getJsonDateTime(Object object) {
        return getJson(object, DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }

    /**
     * 设置其他日期格式
     *
     * @param object     传入的待转换格式的日期对象
     * @param dateFormat 自定义的日期格式
     * @return json字符串
     */
    public static String getJson(Object object, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        //关闭时间戳的功能
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //转换时间格式
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        //让mapper指定时间日期格式为SimpleDateFormat
        mapper.setDateFormat(sdf);
        //将日期对象转换为json字符串格式
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置其他日期格式
     *
     * @param object 传入的待转换格式的日期对象
     * @return json字符串
     */
    public static String getJsonDate(Object object) {
        return getJson(object, DateUtils.YYYY_MM_DD);
    }

//    /**
//     * json转换为object（推荐使用：携带日期格式）
//     */
//    public static String objToStr(Object object) {
//        return JSON.toJSONStringWithDateFormat(object,
//                DateUtils.YYYY_MM_DD,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteDateUseDateFormat);
//    }
//
//    /**
//     * json转换为object（推荐使用：携带日期格式）
//     */
//    public static String objToStr(Object object, String dateFormat) {
//        return JSON.toJSONStringWithDateFormat(object,
//                dateFormat,
//                SerializerFeature.WriteDateUseDateFormat,
//                SerializerFeature.WriteMapNullValue);
//    }

    /**
     * 字符串转JSONObject
     */
    public static JSONObject objToStr(String str) {
        return JSONObject.parseObject(str);
    }

    public static String toJsonString(Object object) {
        if (Objects.isNull(object)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(text, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(byte[] bytes, Class<T> clazz) {
        if (Objects.isNull(bytes) || bytes.length == 0) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(bytes, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(String text, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(text, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if (StringUtils.isEmpty(text)) {
            return new ArrayList<>();
        }
        try {
            return OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * datetime格式转换为date格式
     *
     * @param json   需要转的json数据
     * @param keyArr key数组
     * @return {@link JSONObject}
     */
    public static JSONObject datetime2Date(JSONObject json, String... keyArr) {
        if (json == null) {
            return new JSONObject();
        }
        if (keyArr.length == 0) {
            Set<String> strings = json.keySet();
            keyArr = strings.toArray(new String[strings.size()]);
        }
        for (int i = 0; i < keyArr.length; i++) {
            String key = keyArr[i];
            String value = json.getString(key);
            if (value == null || value.indexOf(DateUtils.HH_MM_SS) == -1) {
                continue;
            }
            json.put(key, value.replaceAll(DateUtils.HH_MM_SS, ""));
        }
        return json;
    }
}
