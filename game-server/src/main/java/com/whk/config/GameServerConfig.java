package com.whk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "game")
public class GameServerConfig {

    private GameDateConfig gameDateConfig;

    private KafkaConfig kafkaConfig;

    @Autowired
    public void setGameDateConfig(GameDateConfig gameDateConfig) {
        this.gameDateConfig = gameDateConfig;
    }

    @Autowired
    public void setKafkaConfig(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    public String getRpcResponseTopic() {
        return "%s-%d-%d".formatted(kafkaConfig.getRpcResponseGameMessageTopic(), gameDateConfig.getZone(), gameDateConfig.getServer());
    }

    public String getRpcRequestTopic(int server) {
        return "%s-%d-%d".formatted(kafkaConfig.getRpcRequestGameMessageTopic(), gameDateConfig.getZone(), server);
    }
}
