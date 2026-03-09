package com.whk.loadconfig;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public abstract class AbstractConfig<T> implements IConfig<T> {

    /**
     * 额外的特殊处理，需要重写
     *
     * @param field 字段
     * @param obj   对象
     * @param value 属性值
     */
    @Override
    public void setValueBySelf(Field field, Object obj, String value) {
        log.warn("此种数据类型没有处理逻辑：%s".formatted(field.getType().getTypeName()));
    }
}
