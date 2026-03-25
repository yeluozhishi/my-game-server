package com.whk.loadconfig.convert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface MapConvertor {

    class IntegerMap implements IConvertor {
        @Override
        public Map<Integer, Integer> convert(Object source) {

            return toIntegerMap(source, Symbol.AND.getSeparatorChars(), Symbol.NUMBER_SIGN.getSeparatorChars());
        }

        public Map<Integer, Integer> toIntegerMap(Object source, String s1, String s2) {
            String value = source.toString();
            if (value.trim().isEmpty()) {
                return Collections.emptyMap();
            }

            String[] valueArray = value.split(s1);
            Map<Integer, Integer> map = new HashMap<>(valueArray.length);
            for (String s : valueArray) {
                String[] rate = s.split(s2);
                map.put(Integer.parseInt(rate[0].trim()), Integer.parseInt(rate[1].trim()));
            }
            return map;
        }
    }

    class StringIntegerMap implements IConvertor {
        @Override
        public Map<String, Integer> convert(Object source) {
            return toStringIntegerMap(source, Symbol.AND.getSeparatorChars(), Symbol.NUMBER_SIGN.getSeparatorChars());
        }

        public Map<String, Integer> toStringIntegerMap(Object source, String s1, String s2) {
            String value = source.toString();
            if (value.trim().isEmpty()) {
                return Collections.emptyMap();
            }
            String[] valueArray = value.split(s1);
            Map<String, Integer> map = new HashMap<>(valueArray.length);
            for (String s : valueArray) {
                String[] rate = s.split(s2);
                map.put(rate[0].trim(), Integer.parseInt(rate[1].trim()));
            }
            return map;
        }
    }

    class StringLongMap implements IConvertor {
        @Override
        public Map<String, Long> convert(Object source) {
            return toStringLongMap(source, Symbol.AND.getSeparatorChars(), Symbol.NUMBER_SIGN.getSeparatorChars());
        }

        public Map<String, Long> toStringLongMap(Object source, String s1, String s2) {
            String value = source.toString();
            if (value.trim().isEmpty()) {
                return Collections.emptyMap();
            }
            String[] valueArray = value.split(s1);
            Map<String, Long> map = new HashMap<>(valueArray.length);
            for (String s : valueArray) {
                String[] rate = s.split(s2);
                map.put(rate[0].trim(), Long.parseLong(rate[1].trim()));
            }
            return map;
        }
    }
}