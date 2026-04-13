package com.whk.loadconfig.convert;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

public class JsonObjectConvertor implements IConvertor {

    @Override
    public Map<String, Long> convert(Object source) {
        String str = (String) source;
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        String format = str.replace("#", ",");
        JSONObject jsonObject = JSONUtil.parseObj(format);
        Map<String, Long> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                if (value instanceof Number) {
                    result.put(entry.getKey(), ((Number) value).longValue());
                } else {
                    result.put(entry.getKey(), Long.parseLong(value.toString()));
                }
            }
        }
        return result;
    }
}