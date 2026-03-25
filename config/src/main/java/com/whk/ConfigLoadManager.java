package com.whk;

import java.util.Objects;

public class ConfigLoadManager {

    public static void init(String path){
        if (Objects.isNull(path) || path.isEmpty()) {
            path = "%s/config/src/main/config/zh_CN/".formatted(System.getProperty("user.dir"));
        }
        LoadCSV loadCSV = new LoadCSV(path);
        loadCSV.loadAll();
//        LoadXml loadXml = new LoadXml("config/zh_CN/");
//        loadXml.loadAll();
    }

}
