package com.whk;

import com.whk.service.GatewayServerBoot;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.whk"})
@EnableScheduling
public class GameGateWayApplication extends SpringApplication {

    public GameGateWayApplication() {
        super();
    }

    public GameGateWayApplication(Class<?>... primarySources) {
        super(primarySources);
    }

    public static void main(String[] args) {
        new GameGateWayApplication(GameGateWayApplication.class).run(args);
    }

    @Override
    protected void afterRefresh(ConfigurableApplicationContext context, ApplicationArguments args) {
        super.afterRefresh(context, args);

        // 从上下文中获取实例
        GatewayServerBoot boot = context.getBean(GatewayServerBoot.class);
        try {
            boot.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        boot.startServerNetty();
    }

    @Override
    protected void refresh(ConfigurableApplicationContext applicationContext) {
        SpringUtils.setContext(applicationContext);
        super.refresh(applicationContext);
    }
}