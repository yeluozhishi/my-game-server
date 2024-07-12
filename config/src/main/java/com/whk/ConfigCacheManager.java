package com.whk;

import com.whk.loadconfig.ConfigReader;

import java.util.HashMap;

public enum ConfigCacheManager {
    INSTANCE;


    private final HashMap<String, ConfigReader<?>> configCache = new HashMap<>();

    public void init(){
        LoadCSV loadCSV = new LoadCSV("E:\\mu\\data\\TrunkData\\table\\serverData");
        loadCSV.loadAll();
        LoadXml loadXml = new LoadXml("config/zh_CN/");
        loadXml.loadAll();

        configCache.putAll(loadCSV.getHashMap());
        configCache.putAll(loadXml.getHashMap());
    }


    public <T extends ConfigReader<?>> T getConfigCache(Class<T> tClass) {
        return (T) configCache.get(tClass.getName());
    }
}
