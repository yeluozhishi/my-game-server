package com.whk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@ConfigurationProperties(prefix = "game")
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
    public void init(){
        setLocalNettyPort();
        setMetadataMap();
    }

    public GameDateConfig getData() {
        return data;
    }

    public KafkaConfig getKafkaConfig() {
        return kafkaConfig;
    }

    public void setLocalNettyPort(){
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

    public String getInstanceId(){
        return eurekaInstanceConfigBean.getInstanceId();
    }
}
