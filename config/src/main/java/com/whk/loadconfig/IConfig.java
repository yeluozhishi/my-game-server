package com.whk.loadconfig;

import java.lang.reflect.Field;
import java.util.List;

public interface IConfig<T> {
    /**
     * 整个xml文件加载完毕后会回调本方法
     * 将结果转为自己定义的数据集合
     *
     * @param list 结果
     */
    void afterLoad(List<T> list);

    void setInstance();

    void setValueBySelf(Field field, Object obj, String value);
}
