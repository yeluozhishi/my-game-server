package com.whk.loadconfig;

import cn.hutool.json.JSONUtil;
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
import java.util.*;

/**
 * @author Administrator
 */
@Slf4j
public class FileCSVConfigReader extends ConfigReader<AbstractConfig<IDefine>> {

    public void load(int skipLine, AbstractConfig<IDefine> config, LoadCSV loadCSV) {
        var annotation = config.getClass().getAnnotation(ConfigInit.class);
        if (annotation.fileName().isBlank()) {
            log.warn("该配置 %s 没有在ConfigInit中设置文件名".formatted(config.getClass().getName()));
            return;
        }
        log.info("%s: 配置开始加载".formatted(annotation.fileName()));
        CSVRecord csvRecord = null;
        try {
            CSVParser csvRecords = loadCSV.loadProcess(annotation.fileName());
            if (Objects.isNull(csvRecords)) return;

            Iterator<CSVRecord> root = csvRecords.stream().iterator();
            List<IDefine> defineList = new LinkedList<>();
            // 跳过指定行
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

            Class<?> clazz = findConfigDefineClazz(config);
            if (Objects.isNull(clazz)) {
                log.warn("该配置 %s 没有继承 AbstractCSVConfig 类型参数".formatted(config.getClass().getName()));
                return;
            }

            // 检查表字段是否缺失
            for (Field declaredField : clazz.getDeclaredFields()) {
                if (!headMap.containsKey(declaredField.getName())) {
                    log.warn("该配置 %s 的配置表的 %s 字段已删除".formatted(annotation.fileName(), declaredField.getName()));
                }
            }

            // 对象赋值
            while (root.hasNext()) {
                csvRecord = root.next();
                Object obj = matchProperties(csvRecord, headMap, clazz, config);
                defineList.add((IDefine) obj);
            }

            if (!defineList.isEmpty()) {
                config.afterLoad(defineList);
            }

            config.setInstance();
        } catch (Exception e) {
            if (Objects.nonNull(csvRecord)) {
                log.error("加载失败 该行数据：%s".formatted(JSONUtil.toJsonStr(csvRecord.values())));
            }
            log.error("加载失败", e);
        }

    }


    /**
     * 匹配数据
     *
     * @param element 元素
     * @param head    字段
     * @param clazz   类
     * @param config  配置
     */
    private Object matchProperties(CSVRecord element, HashMap<String, Integer> head, Class<?> clazz, AbstractConfig<IDefine> config) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object obj = clazz.getDeclaredConstructor().newInstance();
        for (Field declaredField : clazz.getDeclaredFields()) {
            var position = head.get(declaredField.getName());
            if (Objects.isNull(position)) continue;
            var value = element.get(position);
            if (Strings.isNullOrEmpty(value)) continue;
            var column = declaredField.getAnnotation(Column.class);
            if (column != null && column.convertor() != null) {
                setValueByColumn(declaredField, obj, column.convertor(), value);
            } else {
                setValueByTypeName(declaredField, config, obj, value);
            }
        }
        return obj;
    }
}
