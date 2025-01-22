package com.whk.scene;

import com.whk.SpringUtils;
import com.whk.scene.map.SceneManager;
import com.whk.scene.server.SceneServerBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GameSceneApplication {

    public static void main(String[] args) {
        var app = new SpringApplication(GameSceneApplication.class);
        // 关闭web服务
        app.setWebApplicationType(WebApplicationType.NONE);
        ApplicationContext context = SpringApplication.run(GameSceneApplication.class, args);
        SpringUtils.setContext(context);
        // 从上下文中获取实例
        SceneServerBoot boot = context.getBean(SceneServerBoot.class);
        boot.init();
    }

}
