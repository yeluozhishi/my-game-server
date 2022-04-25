package com.whk.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayServerConfig {

    private int bossThreadCount = 5;
    private int workThreadCount = 5;
    private int port = 6020;

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
}
