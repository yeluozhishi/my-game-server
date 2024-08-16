package com.whk.loadconfig;

import com.whk.loadconfig.convert.IConvertor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Logger;

@Getter
public abstract class ConfigReader<T> {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private final Class<T> clazz;

    public ConfigReader() {
        this.clazz = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 通过注解，特殊处理设置值
     * @param declaredField 字段
     * @param obj 对象
     * @param convert 转化对象
     * @param value 属性值
     */
    public void setValueByColumn(Field declaredField, T obj, Class<? extends IConvertor> convert, String value) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        var convertObj = (IConvertor) convert.getDeclaredConstructor().newInstance();
        var result = convertObj.convert(value);
        declaredField.set(obj, result);
    }

    /**
     * 通过字段类型，设置值
     *
     * @param field     字段
     * @param obj       对象
     * @param attribute xml属性值
     */
    public void setValueByTypeName(Field field, T obj, String attribute) throws IllegalAccessException {
        switch (field.getType().getTypeName()) {
            case "java.lang.Integer", "int" -> {
                field.setAccessible(true);
                field.set(obj, Integer.parseInt(attribute));
            }

            case "long" -> {
                field.setAccessible(true);
                field.set(obj, Long.valueOf(attribute));
            }

            case "double" -> {
                field.setAccessible(true);
                field.set(obj, Double.valueOf(attribute));
            }

            case "java.lang.String" -> {
                field.setAccessible(true);
                field.set(obj, attribute);
            }

            case "boolean" -> {
                field.setAccessible(true);
                field.set(obj, Boolean.valueOf(attribute));
            }
            case "int[]", "java.lang.Integer[]" -> {
                var v = Arrays.stream(attribute.split(",")).mapToInt(Integer::parseInt).toArray();
                field.setAccessible(true);
                field.set(obj, v);
            }

            default -> setValueBySelf(field, obj, attribute);

        }
    }

    /**
     * 额外的特殊处理，需要重写
     *
     * @param field     字段
     * @param obj       对象
     * @param value 属性值
     */
    protected void setValueBySelf(Field field, T obj, String value) {
        logger.warning("此种数据类型没有处理逻辑：%s".formatted(field.getType().getTypeName()));
    }

    /**
     * 整个xml文件加载完毕后会回调本方法
     * 将结果转为自己定义的数据类型
     *
     * @param linkedList 结果
     */
    protected abstract void afterLoad(LinkedList<T> linkedList);
}
