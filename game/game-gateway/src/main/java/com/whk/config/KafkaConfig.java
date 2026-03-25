package com.whk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game.kafka-topic")
@Getter
@Setter
public class KafkaConfig {

    private int groupId;

    private String messageTopic;

    private String rpcRequestGameMessageTopic;

    private String rpcResponseGameMessageTopic;

}
