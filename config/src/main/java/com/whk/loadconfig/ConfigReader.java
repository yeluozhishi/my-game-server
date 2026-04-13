package com.whk.loadconfig;

import com.whk.loadconfig.convert.IConvertor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

@Getter
@Slf4j
public abstract class ConfigReader<D extends IDefine, T extends IConfig<D>> {

    /**
     * 通过注解，特殊处理设置值
     *
     * @param declaredField 字段
     * @param obj           对象
     * @param convert       转化对象
     * @param value         属性值
     */
    public void setValueByColumn(Field declaredField, Object obj, Class<? extends IConvertor> convert, String value) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
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
    public void setValueByTypeName(Field field, T config, Object obj, String attribute) throws IllegalAccessException {
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

            default -> config.setValueBySelf(field, obj, attribute);

        }
    }

    public Class<?> findConfigDefineClazz(T config) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 获取当前对象继承的带有泛型参数的父类类型
        Type genericSuperclass = config.getClass().getGenericSuperclass();
        // 转换为 ParameterizedType
        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            // 获取第一个类型参数（即 T）
            Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];

            // 如果类型参数是 Class 类型
            if (actualTypeArgument instanceof Class<?> clazz) {
                // 可以用来创建新实例
                return clazz;
            }
        }
        return null;
    }

    public void load(int i, T config) {

    }
}
