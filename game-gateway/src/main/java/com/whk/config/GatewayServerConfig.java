package com.whk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game")
public class GatewayServerConfig {

    private GameDateConfig data;

    private KafkaConfig kafkaConfig;

    @Autowired
    public void setKafkaConfig(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    @Autowired
    public void setData(GameDateConfig data) {
        this.data = data;
    }

    public GameDateConfig getData() {
        return data;
    }

    public KafkaConfig getKafkaConfig() {
        return kafkaConfig;
    }
}
