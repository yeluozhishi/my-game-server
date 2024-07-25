package com.whk.loadconfig.convert;

import com.whk.GsonUtil;

import java.util.HashMap;
import java.util.Map;

public class JsonObjectConverter implements IConvert {

    @Override
    public Map<String, Long> convert(Object source) {
        String str = (String) source;
        if (str == null || str.trim().isEmpty()) {
            return null;
        }
        String format = str.replace("#", ",");
        return new HashMap<>(GsonUtil.INSTANCE.gsonToMapsLong(format));
    }
}