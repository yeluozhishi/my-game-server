package com.whk;

import com.whk.server.GameServerBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GameApplication {
    public static void main(String[] args) {
        var app = new SpringApplication(GameApplication.class);
        // 关闭web服务
        app.setWebApplicationType(WebApplicationType.NONE);
        ApplicationContext context = app.run(args);
        // 从上下文中获取实例
        GameServerBoot boot = context.getBean(GameServerBoot.class);
        boot.init();
    }
}
