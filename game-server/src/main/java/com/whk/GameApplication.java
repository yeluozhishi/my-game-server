package com.whk;

import com.whk.service.GameServerBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GameApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(GameApplication.class, args);
        // 从上下文中获取实例
        GameServerBoot boot = context.getBean(GameServerBoot.class);
        boot.init();
    }
}
