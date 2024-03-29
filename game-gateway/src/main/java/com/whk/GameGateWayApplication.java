package com.whk;

import com.whk.service.GatewayServerBoot;
import com.whk.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableEurekaClient
public class GameGateWayApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GameGateWayApplication.class, args);
        SpringUtil.setAppContext(context);
        // 从上下文中获取实例
        GatewayServerBoot boot = context.getBean(GatewayServerBoot.class);
        boot.init();
        boot.startServer();
    }
}
