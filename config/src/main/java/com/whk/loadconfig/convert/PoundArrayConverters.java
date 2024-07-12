package com.whk.loadconfig.convert;


import com.whk.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface PoundArrayConverters {

	/**
	 * 井号 "#"
	 */
	class PoundToString implements IConvert {
		@Override
		public String[] convert(Object source) {
			return StringUtils.split((String) source, Symbol.NUMBER_SIGN.getSeparatorChars());
		}
	}

	class PoundToInteger implements IConvert {
		@Override
		public Integer[] convert(Object source) {
			return Arrays.stream(StringUtils.split(String.valueOf(source), Symbol.NUMBER_SIGN.getSeparatorChars()))
					.map(Integer::parseInt).toArray(Integer[]::new);
		}
	}

	class PoundToLong implements IConvert {
		@Override
		public Long[] convert(Object source) {
			return Arrays.stream(StringUtils.split(String.valueOf(source), Symbol.NUMBER_SIGN.getSeparatorChars()))
					.map(Long::parseLong).toArray(Long[]::new);
		}
	}

	class PoundToIntegerSet implements IConvert {
		@Override
		public Set<Integer> convert(Object source) {
			return Arrays.stream(StringUtils.split(String.valueOf(source), Symbol.NUMBER_SIGN.getSeparatorChars()))
					.map(Integer::parseInt).collect(Collectors.toSet());
		}
	}

	class PoundToIntegerList implements IConvert {
		@Override
		public List<Integer> convert(Object source) {
			return Arrays.stream(StringUtils.split(String.valueOf(source), Symbol.NUMBER_SIGN.getSeparatorChars()))
					.map(Integer::parseInt).collect(Collectors.toList());
		}
	}
}
