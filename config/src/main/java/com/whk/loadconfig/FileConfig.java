package com.whk.loadconfig;

import com.whk.LoadXml;
import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.annotation.ConfigInit;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author Administrator
 */
public abstract class FileConfig<T> {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private Class<T> clazz;

    public void load() {
        var annotation = this.getClass().getAnnotation(ConfigInit.class);
        if (annotation.fileName().isBlank()){
            logger.warning("该配置" + this.getClass().getName() + "没有在 ConfigInit 中设置文件名");
            return;
        }

        initClazz();

        LoadXml loadXml = LoadXml.getInstance();

        Document document;
        try {
            document = loadXml.loadProcess(annotation.fileName());
            transformToConfig(document);
        } catch (IOException | DocumentException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            logger.warning(annotation.fileName() + ": 配置加载完成");
        }

    }

    private void initClazz(){
        clazz = (Class<T>) ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    private void transformToConfig(Document document) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Element root = document.getRootElement();

        LinkedList<T> linkedList = new LinkedList<>();

        // iterate through child elements of root
        for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
            Element element = it.next();
            linkedList.add(matchProperties(element));
        }

        if (!linkedList.isEmpty()){
            afterLoad(linkedList);
        }
    }

    /**
     * 匹配数据，返回数据对象
     * @param element 元素
     * @return T 对象
     */
    private T matchProperties(Element element) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var obj = clazz.getDeclaredConstructor().newInstance();
        for (Field declaredField : clazz.getDeclaredFields()) {
            var attr = element.attribute(declaredField.getName());
            if (attr != null){
                var column = declaredField.getAnnotation(Column.class);
                if (column != null){
                    setValueByColumn(declaredField, obj, column, attr);
                } else {
                    setValueByTypeName(declaredField, obj, attr);
                }
            }
        }
        return obj;
    }

    /**
     * 通过注解，特殊处理设置值
     * @param declaredField 字段
     * @param obj 对象
     * @param column 字段注解对象
     * @param attribute xml属性值
     */
    private void setValueByColumn(Field declaredField, T obj, Column column, Attribute attribute) throws IllegalAccessException {
        switch (column.column()){
            case LIST_INT ->{
                var v = Arrays.stream(attribute.getValue().split(",")).map(Integer::parseInt).toList();
                declaredField.setAccessible(true);
                declaredField.set(obj, v);
            }

            case LIST_STRING ->{
                var v = Arrays.stream(attribute.getValue().split(",")).toList();
                declaredField.setAccessible(true);
                declaredField.set(obj, v);
            }

            default -> logger.warning("该特殊处理列，没有处理逻辑：" + column.column());
        }
    }

    /**
     * 通过字段类型，设置值
     * @param field 字段
     * @param obj 对象
     * @param attribute xml属性值
     */
    private void setValueByTypeName(Field field, T obj, Attribute attribute) throws IllegalAccessException {
        switch (field.getType().getTypeName()){
            case "int" -> {
                field.setAccessible(true);
                field.set(obj, Integer.parseInt(attribute.getValue()));
            }

            case "long" -> {
                field.setAccessible(true);
                field.set(obj, Long.valueOf(attribute.getValue()));
            }

            case "double" -> {
                field.setAccessible(true);
                field.set(obj, Double.valueOf(attribute.getValue()));
            }

            case "java.lang.String" -> {
                field.setAccessible(true);
                field.set(obj, attribute.getValue());
            }

            case "boolean" -> {
                field.setAccessible(true);
                field.set(obj, Boolean.valueOf(attribute.getValue()));
            }
            case "int[]" -> {
                var v = Arrays.stream(attribute.getValue().split(",")).mapToInt(Integer::parseInt).toArray();
                field.setAccessible(true);
                field.set(obj, v);
            }

            default -> setValueBySelf(field, obj, attribute);

        }
    }

    /**
     * 额外的特殊处理，需要重写
     * @param field 字段
     * @param obj 对象
     * @param attribute xml属性值
     */
    protected void setValueBySelf(Field field, T obj, Attribute attribute){
        logger.warning("此种数据类型没有处理逻辑：" + field.getType().getName());
    }

    /**
     * 整个xml文件加载完毕后会回调本方法
     * 将结果转为自己定义的数据类型
     * @param linkedList 结果
     */
    protected abstract void afterLoad(LinkedList<T> linkedList);

}
