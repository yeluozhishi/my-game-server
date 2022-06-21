package com.whk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Configuration;

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
        config.getData().setPort(config.getData().getPort() + 1);
    }

    public void setInstanceId() {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        config.getData().setInstanceId(eurekaInstanceConfigBean.getInstanceId());
        assert localHost != null;
        String ip = localHost.getHostAddress();
        eurekaInstanceConfigBean.setInstanceId(eurekaInstanceConfigBean.getInstanceId() + ":" + ip + ":" + config.getData().getPort());
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
        map.put("port", String.valueOf(config.getData().getPort()));
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
