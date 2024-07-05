package com.whk.client.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class GameClientConfig {
    // 线程数
    private int workThreads = 16;

    private int connectTimeOut = 10;

    private String defaultGameGatewayHost = "127.0.0.1";

    private int defaultGameGatewayPort = 6001;

    private boolean useGameCenter = true;

    private String gameCenterUrl = "http://127.0.0.1:5020/WEB-CENTER";

    private String token;

    private String instanceId;

    private int zone;

    public int getWorkThreads() {
        return workThreads;
    }

    public void setWorkThreads(int workThreads) {
        this.workThreads = workThreads;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public String getDefaultGameGatewayHost() {
        return defaultGameGatewayHost;
    }

    public void setDefaultGameGatewayHost(String defaultGameGatewayHost) {
        this.defaultGameGatewayHost = defaultGameGatewayHost;
    }

    public int getDefaultGameGatewayPort() {
        return defaultGameGatewayPort;
    }

    public void setDefaultGameGatewayPort(int defaultGameGatewayPort) {
        this.defaultGameGatewayPort = defaultGameGatewayPort;
    }

    public boolean isUseGameCenter() {
        return useGameCenter;
    }

    public void setUseGameCenter(boolean useGameCenter) {
        this.useGameCenter = useGameCenter;
    }

    public String getGameCenterUrl() {
        return gameCenterUrl;
    }

    public void setGameCenterUrl(String gameCenterUrl) {
        this.gameCenterUrl = gameCenterUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }
}
