package com.whk.loadconfig;

import com.whk.LoadCSV;
import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.annotation.ConfigInit;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.assertj.core.util.Strings;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * @author Administrator
 */
public abstract class FileCSVConfig<T> extends ConfigReader<T> {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private int skipLine;

    public void load(int skipLine, LoadCSV loadCSV) {
        var annotation = this.getClass().getAnnotation(ConfigInit.class);
        if (annotation.fileName().isBlank()) {
            logger.warning("该配置%s没有在ConfigInit中设置文件名".formatted(this.getClass().getName()));
            return;
        }
        this.skipLine = skipLine;
        try {
            CSVParser csvRecords = loadCSV.loadProcess(annotation.fileName());
            if (Objects.isNull(csvRecords)) return;
            transformToConfig(csvRecords);
        } catch (IOException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            logger.info("%s: 配置加载完成".formatted(annotation.fileName()));
        }

    }


    private void transformToConfig(CSVParser csvRecords) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Iterator<CSVRecord> root = csvRecords.stream().iterator();

        LinkedList<T> linkedList = new LinkedList<>();

        int skip = skipLine;
        while (skip > 0) {
            root.next();
            skip--;
        }

        var head = root.next();

        // iterate through child elements of root
        while (root.hasNext()) {
            CSVRecord element = root.next();
            linkedList.add(matchProperties(element, head));
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
    private T matchProperties(CSVRecord element, CSVRecord head) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        var obj = getClazz().getDeclaredConstructor().newInstance();
        for (int i = 0; i < head.size(); i++) {
            if (Strings.isNullOrEmpty(element.get(i))) continue;
            var declaredField = getClazz().getDeclaredField(head.get(i));
            var column = declaredField.getAnnotation(Column.class);
            if (column != null && column.converter() != null) {
                setValueByColumn(declaredField, obj, column.converter(), element.get(i));
            } else {
                setValueByTypeName(declaredField, obj, element.get(i));
            }
        }
        return obj;
    }
}