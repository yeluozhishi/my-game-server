package com.whk;

import java.util.Objects;

public class ConfigLoadManager {

    public static void init(String path){
        if (Objects.isNull(path) || path.isEmpty()) {
            path = "config/zh_CN/";
        }
        LoadCSV loadCSV = new LoadCSV(path);
        loadCSV.loadAll();
//        LoadXml loadXml = new LoadXml("config/zh_CN/");
//        loadXml.loadAll();
    }

}
