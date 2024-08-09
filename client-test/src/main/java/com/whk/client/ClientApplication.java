package com.whk.client;

import com.whk.SpringUtils;
import com.whk.client.component.InputThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.whk"})
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ClientApplication.class);
        // 关闭web服务
        app.setWebApplicationType(WebApplicationType.NONE);
        var context = app.run(args);
        SpringUtils.setContext(context);
        InputThread inputThread = new InputThread();
        inputThread.start();
    }

}
