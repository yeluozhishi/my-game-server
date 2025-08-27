package com.whk.config;

import com.whk.ConfigCacheManager;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadConfig {

    private CenterConfig config;

    @Autowired
    public void setConfig(CenterConfig config) {
        this.config = config;
    }

    @PostConstruct
    public void init() {
        long start = System.currentTimeMillis();
        ConfigCacheManager.INSTANCE.init();
        System.out.println("LoadConfig time:" + (System.currentTimeMillis() - start));
    }
}
