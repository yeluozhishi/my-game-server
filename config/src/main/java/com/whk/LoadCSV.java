package com.whk;

import com.whk.loadconfig.FileCSVConfig;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.assertj.core.util.Strings;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

/**
 * 需求：文件名
 * 步骤：读取文件 -> 通过反射将值赋予对象（注解处理特殊字段） -> 返回 list 列表 -> 各自处理为所需数据格式
 *
 * @author Administrator
 */
@Getter
public class LoadCSV {
    private static final String SUFFIX = ".csv";
    private CSVFormat reader;

    private HashMap<String, URL> filePath;

    private final HashMap<String, FileCSVConfig<?>> hashMap = new HashMap<>();

    public LoadCSV(String path) {
        if (Strings.isNullOrEmpty(path)) {
            return;
        }
        reader = CSVFormat.DEFAULT.builder().build();
        filePath = new HashMap<>();

        try {
            File file = new File(path);
            addAllXmlFiles(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadAll() {
        Reflections reflections = new Reflections(this.getClass().getPackageName());
        var subTypes = reflections.getSubTypesOf(FileCSVConfig.class);
        subTypes.forEach(x -> {
            FileCSVConfig<?> obj;
            try {
                obj = x.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            obj.load(3, this);
            hashMap.put(x.getName(), obj);
        });
    }

    /**
     * 加载所有xml文件路径
     *
     * @param file 文件
     */
    private void addAllXmlFiles(File file) throws IOException {
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isFile() && (f.getName().endsWith(SUFFIX))) {
                filePath.put(f.getName().split("\\.")[0], f.toURI().toURL());
            } else if (f.isDirectory()) {
                addAllXmlFiles(f);
            }
        }
    }


    public CSVParser loadProcess(String fileName) throws IOException {
        var url = filePath.get(fileName);
        if (Objects.isNull(url)) return null;
        var fileReader = new FileReader(url.getPath());
        return reader.parse(fileReader);
    }
}
