package com.whk.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Configuration;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@ConfigurationProperties(prefix = "game")
@Getter
@Setter
public class GatewayServerConfig {

    private GameDateConfig data;

    private KafkaConfig kafkaConfig;

    private EurekaInstanceConfigBean eurekaInstanceConfigBean;

    @Autowired
    public void setKafkaConfig(KafkaConfig kafkaConfig) {
        this.kafkaConfig = kafkaConfig;
    }

    @Autowired
    public void setData(GameDateConfig data) {
        this.data = data;
    }

    @Autowired
    public void setEurekaInstanceConfigBean(EurekaInstanceConfigBean eurekaInstanceConfigBean) {
        this.eurekaInstanceConfigBean = eurekaInstanceConfigBean;
    }

    @PostConstruct
    public void init() {
        setLocalNettyPort();
        setMetadataMap();
    }

    public void setLocalNettyPort() {
        data.setPort(data.getPort() + 1);
    }

    public void setMetadataMap() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        assert localHost != null;
        String ip = localHost.getHostAddress();
        var map = eurekaInstanceConfigBean.getMetadataMap();
        map.put("ip", ip);
        map.put("port", String.valueOf(data.getPort()));
        eurekaInstanceConfigBean.setMetadataMap(map);
    }

    public String getTopic() {
        return "%s-%d-%d".formatted(kafkaConfig.getMessageTopic(), data.getZone(), data.getServer());
    }

    public String getRpcRequestTopic(int server) {
        return "%s-%d-%d".formatted(kafkaConfig.getRpcRequestGameMessageTopic(), data.getZone(), server);
    }

    public String getRpcResponseTopic() {
        return "%s-%d-%d".formatted(kafkaConfig.getRpcResponseGameMessageTopic(), data.getZone(), data.getServer());
    }
}
