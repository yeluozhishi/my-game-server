package com.whk.config;

import com.whk.constant.TopicConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "game.kafka-topic")
public class KafkaConfig {

    private int groupId;

    private int server;

    private String rpcRequestGameMessageTopic;

    private String rpcResponseGameMessageTopic;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public String getRpcRequestGameMessageTopic() {
        return rpcRequestGameMessageTopic;
    }

    public void setRpcRequestGameMessageTopic(String rpcRequestGameMessageTopic) {
        this.rpcRequestGameMessageTopic = rpcRequestGameMessageTopic;
        TopicConstants.REQUEST_TOPIC = TopicConstants.build(rpcRequestGameMessageTopic);
    }

    public String getRpcResponseGameMessageTopic() {
        return rpcResponseGameMessageTopic;
    }

    public void setRpcResponseGameMessageTopic(String rpcResponseGameMessageTopic) {
        this.rpcResponseGameMessageTopic = rpcResponseGameMessageTopic;
        TopicConstants.RESPONSE_TOPIC = TopicConstants.build(rpcResponseGameMessageTopic);
    }
}
