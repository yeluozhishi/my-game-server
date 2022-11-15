package com.whk.constant;

/**
 * RPC topic 前缀
 */
public enum TopicConstants {

    REQUEST_TOPIC("rpc-request-message-topic"),
    RESPONSE_TOPIC("rpc-response-message-topic");

    private String topic;

    TopicConstants(String topic){
        this.topic = topic;
    }

    public String getTopic(int serverId){
        return this.topic + serverId;
    }
}
