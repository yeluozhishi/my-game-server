package com.whk.loadconfig;

import com.whk.LoadXml;
import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.annotation.ConfigInit;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Administrator
 */
public abstract class FileXMLConfig<T> extends ConfigReader<T> {

    Logger logger = Logger.getLogger(this.getClass().getName());

    public void load(LoadXml loadXml) {
        var annotation = this.getClass().getAnnotation(ConfigInit.class);
        if (annotation.fileName().isBlank()) {
            logger.warning("该配置%s没有在ConfigInit中设置文件名".formatted(this.getClass().getName()));
            return;
        }

        try {
            Document document = loadXml.loadProcess(annotation.fileName());
            if (Objects.isNull(document)) return;
            transformToConfig(document);
        } catch (IOException | DocumentException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            logger.info("%s: 配置加载完成".formatted(annotation.fileName()));
        }

    }


    private void transformToConfig(Document document) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        Element root = document.getRootElement();

        LinkedList<T> linkedList = new LinkedList<>();

        // iterate through child elements of root
        for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
            Element element = it.next();
            linkedList.add(matchProperties(element));
        }

        if (!linkedList.isEmpty()) {
            afterLoad(linkedList);
        }
    }

    /**
     * 匹配数据，返回数据对象
     *
     * @param element 元素
     * @return T 对象
     */
    private T matchProperties(Element element) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var obj = getClazz().getDeclaredConstructor().newInstance();
        for (Field declaredField : getClazz().getDeclaredFields()) {
            declaredField.setAccessible(true);
            var attr = element.attribute(declaredField.getName());
            if (Objects.isNull(attr)) continue;
            var column = declaredField.getAnnotation(Column.class);
            if (column != null && column.convertor() != null) {
                setValueByColumn(declaredField, obj, column.convertor(), attr.getValue());
            } else {
                setValueByTypeName(declaredField, obj, attr.getValue());
            }
        }
        return obj;
    }

}
