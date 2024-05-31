package com.whk.config;

import com.whk.LoadXml;
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
    public void init(){
        var loadXml = LoadXml.getInstance(config.getXmlPath());
        loadXml.loadAll();

    }
}
