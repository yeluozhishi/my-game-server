package com.whk.constant;

/**
 * RPC topic 前缀
 */
public enum TopicConstants {

    // 请求
    REQUEST_TOPIC("rpc-request-game-message-topic-"),
    //回复
    RESPONSE_TOPIC("rpc-response-game-message-topic-");

    private final String topic;

    TopicConstants(String topic){
        this.topic = topic;
    }

    public String getTopic(int serverId){
        return this.topic + serverId;
    }
}
