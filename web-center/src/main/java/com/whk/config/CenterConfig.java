package com.whk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "game")
public class CenterConfig {

    /**
     * xml 文件路径
     */
    private String xmlPath;

}
