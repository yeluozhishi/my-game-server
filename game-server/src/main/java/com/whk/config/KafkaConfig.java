package com.whk.config;

import com.whk.constant.TopicConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game.kafka-topic")
@Setter
@Getter
public class KafkaConfig {

    @Setter
    private int groupId;

    private String rpcRequestGameMessageTopic;

    private String rpcResponseGameMessageTopic;

    public void setRpcRequestGameMessageTopic(String rpcRequestGameMessageTopic) {
        this.rpcRequestGameMessageTopic = rpcRequestGameMessageTopic;
        TopicConstants.REQUEST_TOPIC = TopicConstants.build(rpcRequestGameMessageTopic);
    }

    public void setRpcResponseGameMessageTopic(String rpcResponseGameMessageTopic) {
        this.rpcResponseGameMessageTopic = rpcResponseGameMessageTopic;
        TopicConstants.RESPONSE_TOPIC = TopicConstants.build(rpcResponseGameMessageTopic);
    }
}
