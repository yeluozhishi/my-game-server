package com.whk.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
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

}
