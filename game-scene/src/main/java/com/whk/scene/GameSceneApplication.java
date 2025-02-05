package com.whk.scene;

import com.whk.SpringUtils;
import script.ScannerClassException;
import com.whk.scene.server.SceneServerBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@SpringBootApplication
public class GameSceneApplication {

    public static void main(String[] args) throws IOException, ScannerClassException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
