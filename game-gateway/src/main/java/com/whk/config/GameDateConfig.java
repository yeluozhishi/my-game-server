package com.whk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game.data")
public class GameDateConfig {
    private int bossThreadCount;
    private int workThreadCount;
    private int port = 6020;

    private String webGate;
    private String instanceId;

    public String getWebGate() {
        return webGate;
    }

    public void setWebGate(String webGate) {
        this.webGate = webGate;
    }

    public int getBossThreadCount() {
        return bossThreadCount;
    }

    public int getWorkThreadCount() {
        return workThreadCount;
    }

    public void setWorkThreadCount(int workThreadCount) {
        this.workThreadCount = workThreadCount;
    }
    public void setBossThreadCount(int bossThreadCount) {
        this.bossThreadCount = bossThreadCount;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }
}
