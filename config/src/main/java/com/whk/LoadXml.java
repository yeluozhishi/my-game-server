package com.whk;

import com.whk.loadconfig.AbstractConfig;
import com.whk.loadconfig.FileXMLConfigReader;
import com.whk.loadconfig.IDefine;
import lombok.Getter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.reflections.Reflections;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 需求：文件名
 * 步骤：读取文件 -> 通过反射将值赋予对象（注解处理特殊字段） -> 返回 list 列表 -> 各自处理为所需数据格式
 *
 * @author Administrator
 */
@Getter
public class LoadXml {
    private final String SUFFIX = ".xml";
    private final SAXReader reader;
    private final String filePath;

    public LoadXml(String path) {
        reader = new SAXReader();
        filePath = path;
    }

    public void loadAll() {
        Reflections reflections = new Reflections(this.getClass().getPackageName());
        var subTypes = reflections.getSubTypesOf(AbstractConfig.class);
        FileXMLConfigReader reader = new FileXMLConfigReader(this);
        subTypes.stream().parallel().forEach(configClass -> {
            try {
                AbstractConfig<IDefine> config = configClass.getDeclaredConstructor().newInstance();
                reader.load(0, config);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

        });
    }

    public Document loadFile(String fileName) throws IOException, DocumentException {
        return reader.read(filePath + fileName + SUFFIX);
    }
}
