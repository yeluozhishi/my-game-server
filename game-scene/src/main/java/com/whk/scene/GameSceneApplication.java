package com.whk.scene;

import com.whk.SpringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import script.ScannerClassException;
import com.whk.scene.server.SceneServerBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
@ComponentScan(basePackages = "com.whk")
public class GameSceneApplication extends SpringApplication{

    public GameSceneApplication() {
        super();
    }

    public GameSceneApplication(Class<?>... primarySources) {
        super(primarySources);
    }

    public static void main(String[] args) {
        var app = new GameSceneApplication(GameSceneApplication.class);
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
        SceneServerBoot boot = context.getBean(SceneServerBoot.class);
        try {
            boot.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
