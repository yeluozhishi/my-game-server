package com.whk;

import com.whk.loadconfig.FileConfig;
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
 * 步骤：读取文件 -》 通过反射将值赋予对象（注解处理特殊字段） -》 返回 list 列表 -》 各自处理为所需数据格式
 * @author Administrator
 */
public class LoadXml {
    private static String PATH = "config/zh_CN/";
    private static final String SUFFIX = ".xml";
    private final SAXReader reader;

    private final HashMap<String, URL> filePath;

    private static LoadXml loadXML;

    private LoadXml() {
        reader = new SAXReader();
        filePath = new HashMap<>();
        var resource = new ClassPathResource(PATH);
        File file;
        try {
            file = resource.getFile();
            addAllXmlFiles(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static LoadXml getInstance(){
        if (loadXML == null){
            loadXML = new LoadXml();
        }
        return loadXML;
    }

    public static LoadXml getInstance(String path){
        if (loadXML == null){
            loadXML = new LoadXml();
        }

        if (path != null && path.isEmpty()){
            PATH = path;
        }
        return loadXML;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        var resource = new ClassPathResource("config/zh_CN/");
        System.out.println("xml路径:" + resource.getPath());
        var load = getInstance();
        load.loadAll();
        System.out.println("document");
    }

    public void loadAll(){
        Reflections reflections = new Reflections(this.getClass().getPackageName());
        var subTypes = reflections.getSubTypesOf(FileConfig.class);
        subTypes.forEach(x -> {
            FileConfig obj;
            try {
                obj = x.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            obj.load();
        });


    }

    /**
     * 加载所有xml文件路径
     * @param file 文件
     */
    private void addAllXmlFiles(File file) throws IOException {
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isFile() && f.getName().endsWith(SUFFIX)){
                filePath.put(f.getName().split("\\.")[0], f.toURI().toURL());
            } else if (f.isDirectory()){
                addAllXmlFiles(f);
            }
        }
    }


    public Document loadProcess(String fileName) throws IOException, DocumentException {
        var url = filePath.get(fileName);
        return loadXML.parse(url);
    }

    /**
     * 解析
     * @param url 路径
     */
    public Document parse(URL url) throws DocumentException {
        return reader.read(url);
    }
}
