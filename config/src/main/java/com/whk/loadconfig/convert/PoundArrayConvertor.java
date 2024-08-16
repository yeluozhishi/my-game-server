package com.whk.loadconfig.convert;


import com.whk.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface PoundArrayConvertor {

    /**
     * 井号 "#"
     */
    class PoundToString implements IConvertor {
        @Override
        public String[] convert(Object source) {
            return StringUtils.split((String) source, Symbol.NUMBER_SIGN.getSeparatorChars());
        }
    }

    class PoundToInteger implements IConvertor {
        @Override
        public Integer[] convert(Object source) {
            return Arrays.stream(StringUtils.split(String.valueOf(source), Symbol.NUMBER_SIGN.getSeparatorChars()))
                    .map(Integer::parseInt).toArray(Integer[]::new);
        }
    }

    class PoundToLong implements IConvertor {
        @Override
        public Long[] convert(Object source) {
            return Arrays.stream(StringUtils.split(String.valueOf(source), Symbol.NUMBER_SIGN.getSeparatorChars()))
                    .map(Long::parseLong).toArray(Long[]::new);
        }
    }

    class PoundToIntegerSet implements IConvertor {
        @Override
        public Set<Integer> convert(Object source) {
            return Arrays.stream(StringUtils.split(String.valueOf(source), Symbol.NUMBER_SIGN.getSeparatorChars()))
                    .map(Integer::parseInt).collect(Collectors.toSet());
        }
    }

    class PoundToIntegerList implements IConvertor {
        @Override
        public List<Integer> convert(Object source) {
            return Arrays.stream(StringUtils.split(String.valueOf(source), Symbol.NUMBER_SIGN.getSeparatorChars()))
                    .map(Integer::parseInt).collect(Collectors.toList());
        }
    }
}
