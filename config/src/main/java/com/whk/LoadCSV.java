package com.whk;

import com.whk.loadconfig.AbstractConfig;
import com.whk.loadconfig.FileCSVConfigReader;
import com.whk.loadconfig.IDefine;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.reflections.Reflections;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 需求：文件名
 * 步骤：读取文件 -> 通过反射将值赋予对象（注解处理特殊字段） -> 返回 list 列表 -> 各自处理为所需数据格式
 *
 * @author Administrator
 */
@Getter
public class LoadCSV {
    private static final String SUFFIX = ".csv";
    private final CSVFormat reader;
    private final String filePath;


    public LoadCSV(String path) {
        reader = CSVFormat.DEFAULT.builder().build();
        filePath = path;
    }

    public void loadAll() {
        Reflections reflections = new Reflections(this.getClass().getPackageName());
        var subTypes = reflections.getSubTypesOf(AbstractConfig.class);
        FileCSVConfigReader reader = new FileCSVConfigReader();
        subTypes.forEach(x -> {
            AbstractConfig<IDefine> config;
            try {
                config = x.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            reader.load(3, config, this);
        });
    }


    public CSVParser loadProcess(String fileName) throws IOException {
        var fileReader = new FileReader(filePath + fileName + SUFFIX);
        return reader.parse(fileReader);
    }
}
