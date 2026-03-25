package com.whk.loadconfig.convert;

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
        return JSONUtil.parse(format).toBean(HashMap.class);
    }
}