package com.whk;

import com.whk.server.GameServerBoot;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
public class GameApplication extends SpringApplication {
    public GameApplication() {
        super();
    }

    public GameApplication(Class<?>... primarySources) {
        super(primarySources);
    }

    public static void main(String[] args) {
        var app = new GameApplication(GameApplication.class);
        // 关闭web服务
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Override
    protected void refresh(ConfigurableApplicationContext applicationContext) {
        SpringUtils.setContext(applicationContext);
        super.refresh(applicationContext);
    }

    @Override
    protected void afterRefresh(ConfigurableApplicationContext context, ApplicationArguments args) {
        super.afterRefresh(context, args);
        // 从上下文中获取实例
        GameServerBoot boot = context.getBean(GameServerBoot.class);
        try {
            boot.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}