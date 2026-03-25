package com.whk.loadconfig;

import cn.hutool.json.JSONUtil;
import com.whk.LoadXml;
import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.annotation.ConfigInit;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 */
@Slf4j
public class FileXMLConfigReader extends ConfigReader<AbstractConfig<IDefine>> {


    public void load(AbstractConfig<IDefine> config, LoadXml loadXml) {
        var annotation = this.getClass().getAnnotation(ConfigInit.class);
        if (annotation.fileName().isBlank()) {
            log.warn("该配置%s没有在ConfigInit中设置文件名".formatted(config.getClass().getName()));
            return;
        }
        log.warn("开始加载 %s 配置表".formatted(annotation.fileName()));
        Element element = null;
        try {
            Document document = loadXml.loadProcess(annotation.fileName());
            if (Objects.isNull(document)) return;

            Element root = document.getRootElement();
            List<IDefine> linkedList = new LinkedList<>();

            // 创建对象
            var clazz = findConfigDefineClazz(config);
            if (Objects.isNull(clazz)) {
                log.warn("该配置 %s 没有继承 AbstractXMLConfig 类型参数".formatted(config.getClass().getName()));
                return;
            }

            // 检查表字段是否缺失
            for (Field declaredField : clazz.getDeclaredFields()) {
                var attr = root.attribute(declaredField.getName());
                if (Objects.isNull(attr)) {
                    log.warn("该配置 %s 的配置表的 %s 字段已删除".formatted(annotation.fileName(), declaredField.getName()));
                }
            }
            // 对象赋值
            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
                element = it.next();
                Object obj = matchProperties(element, clazz, config);
                linkedList.add((IDefine) obj);
            }

            if (!linkedList.isEmpty()) {
                config.afterLoad(linkedList);
            }

            config.setInstance();
        } catch (IOException | DocumentException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            if (Objects.nonNull(element))
                log.error("加载失败 该行数据：%s".formatted(JSONUtil.toJsonStr(element.attributes())));
            throw new RuntimeException(e);
        }
    }

    /**
     * 匹配数据
     *
     * @param element 元素
     * @param clazz   类
     * @param config  配置
     */
    private Object matchProperties(Element element, Class<?> clazz, AbstractConfig<IDefine> config) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object obj = clazz.getDeclaredConstructor().newInstance();
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            var attr = element.attribute(declaredField.getName());
            if (Objects.isNull(attr)) continue;
            var column = declaredField.getAnnotation(Column.class);
            if (column != null && column.convertor() != null) {
                setValueByColumn(declaredField, obj, column.convertor(), attr.getValue());
            } else {
                setValueByTypeName(declaredField, config, obj, attr.getValue());
            }
        }
        return obj;
    }

}
