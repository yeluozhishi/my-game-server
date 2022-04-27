package com.whk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 重置 EurekaInstanceConfigBean 的 instanceId
 */
@Configuration
public class MyEurekaInstanceConfigBean{
    /**
     * 配置信息
     */
    private GatewayServerConfig config;

    private EurekaInstanceConfigBean eurekaInstanceConfigBean;

    @PostConstruct
    public void init(){
        setLocalNettyPort();
        setInstanceId();
        setMetadataMap();
    }

    public void setLocalNettyPort(){
        config.setPort(config.getPort() + 1);
    }

    public void setInstanceId() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = localHost.getHostAddress();
        eurekaInstanceConfigBean.setInstanceId(eurekaInstanceConfigBean.getInstanceId() + ":" + ip + ":" + config.getPort());
    }

    public void setMetadataMap() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = localHost.getHostAddress();
        var map = eurekaInstanceConfigBean.getMetadataMap();
        map.put("ip", ip);
        map.put("port", String.valueOf(config.getPort()));
        eurekaInstanceConfigBean.setMetadataMap(map);
    }

    @Autowired
    public void setConfig(GatewayServerConfig config) {
        this.config = config;
    }

    @Autowired
    public void setEurekaInstanceConfigBean(EurekaInstanceConfigBean eurekaInstanceConfigBean) {
        this.eurekaInstanceConfigBean = eurekaInstanceConfigBean;
    }
}
