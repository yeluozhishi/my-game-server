package com.whk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game.data")
@Setter
@Getter
public class GameDateConfig {

    private int server;

    private int zone;

    private boolean dev;

    private String artifactId;

    private String scriptArtifactId;

}
