package com.whk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game.data")
@Getter
@Setter
public class GameDateConfig {
    private int bossThreadCount;

    private int workThreadCount;

    private int port = 6020;

    private int server = 1;

    private int zone = 1;

    private boolean dev;

    private String artifactId;

    private String scriptArtifactId;
}
