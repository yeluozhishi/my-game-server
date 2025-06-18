package com.whk.loadconfig;

import com.whk.LoadCSV;
import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.annotation.ConfigInit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.assertj.core.util.Strings;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author Administrator
 */
@Slf4j
public abstract class FileCSVConfig<T> extends ConfigReader<T> {

    public void load(int skipLine, LoadCSV loadCSV) {
        var annotation = this.getClass().getAnnotation(ConfigInit.class);
        if (annotation.fileName().isBlank()) {
            log.warn("该配置%s没有在ConfigInit中设置文件名".formatted(this.getClass().getName()));
            return;
        }
        try {
            CSVParser csvRecords = loadCSV.loadProcess(annotation.fileName());
            if (Objects.isNull(csvRecords)) return;
            transformToConfig(csvRecords, skipLine);
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            log.info("%s: 配置加载完成".formatted(annotation.fileName()));
        }

    }


    private void transformToConfig(CSVParser csvRecords, int skipLine) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Iterator<CSVRecord> root = csvRecords.stream().iterator();

        LinkedList<T> linkedList = new LinkedList<>();

        int skip = skipLine;
        while (skip > 0) {
            root.next();
            skip--;
        }

        var head = root.next();
        HashMap<String, Integer> headMap = new HashMap<>();
        for (int i = 0; i < head.size(); i++) {
            headMap.put(head.get(i), i);
        }

        // iterate through child elements of root
        while (root.hasNext()) {
            CSVRecord element = root.next();
            linkedList.add(matchProperties(element, headMap));
        }

        if (!linkedList.isEmpty()) {
            afterLoad(linkedList);
        }
    }

    /**
     * 匹配数据，返回数据对象
     *
     * @param element 元素
     * @param head 字段
     * @return T 对象
     */
    private T matchProperties(CSVRecord element, HashMap<String, Integer> head) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        var obj = getClazz().getDeclaredConstructor().newInstance();

        for (Field declaredField : getClazz().getDeclaredFields()) {
            var position = head.get(declaredField.getName());
            if (Objects.isNull(position)) {
                log.warn("该配置%s的配置表的 %s 字段已删除".formatted(this.getClass().getName(), declaredField.getName()));
                continue;
            }
            var value = element.get(position);
            if (Strings.isNullOrEmpty(value)) continue;
            var column = declaredField.getAnnotation(Column.class);
            if (column != null && column.convertor() != null) {
                setValueByColumn(declaredField, obj, column.convertor(), value);
            } else {
                setValueByTypeName(declaredField, obj, value);
            }
        }
        return obj;
    }
}
