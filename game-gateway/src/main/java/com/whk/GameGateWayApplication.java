package com.whk;

import com.whk.service.GatewayServerBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.whk.SpringUtils;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.whk"})
@EnableScheduling
public class GameGateWayApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GameGateWayApplication.class, args);
        SpringUtils.setContext(context);
        // 从上下文中获取实例
        GatewayServerBoot boot = context.getBean(GatewayServerBoot.class);
        boot.init();
        boot.startServer();
    }
}
