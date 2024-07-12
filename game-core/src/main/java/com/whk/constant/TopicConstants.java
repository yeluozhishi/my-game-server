package com.whk.constant;

/**
 * RPC topic 前缀
 */
public class TopicConstants {
    // 请求
    public static Topic MESSAGE_TOPIC;
    // 请求
    public static Topic REQUEST_RPC_TOPIC;
    //回复
    public static Topic RESPONSE_RPC_TOPIC;

    public static Topic build(String topic){
        return new Topic(topic);
    }

    public record Topic(String topic){
        public String getTopic(int zone, int serverId){
            return this.topic+ "-" + zone + "-" + serverId;
        }
    }
}
