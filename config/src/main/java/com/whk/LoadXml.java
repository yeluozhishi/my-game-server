package com.whk;

import com.whk.loadconfig.FileXMLConfig;
import lombok.Getter;
import org.assertj.core.util.Strings;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.reflections.Reflections;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
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
public class LoadXml {
    private final String SUFFIX = ".xml";
    private final SAXReader reader;

    private final HashMap<String, URL> filePath;

    private final HashMap<String, FileXMLConfig<?>> hashMap = new HashMap<>();

    public LoadXml(String path) {
        assert Strings.isNullOrEmpty(path);
        reader = new SAXReader();
        filePath = new HashMap<>();
        try {
            addAllXmlFiles(new ClassPathResource(path).getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadAll() {
        Reflections reflections = new Reflections(this.getClass().getPackageName());
        var subTypes = reflections.getSubTypesOf(FileXMLConfig.class);
        subTypes.forEach(x -> {
            FileXMLConfig<?> obj;
            try {
                obj = x.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            obj.load(this);
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


    public Document loadProcess(String fileName) throws IOException, DocumentException {
        var url = filePath.get(fileName);
        if (Objects.isNull(url)) return null;
        return reader.read(url);
    }
}
