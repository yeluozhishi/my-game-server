package com.whk.client;

import com.whk.SpringUtils;
import com.whk.client.component.GameClientCommand;
import com.whk.client.component.InputThread;
import com.whk.listener.HeartbeatListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(scanBasePackages = {"com.whk"})
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {HeartbeatListener.class})})
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ClientApplication.class);
        // 关闭web服务
        app.setWebApplicationType(WebApplicationType.NONE);
        var context = app.run(args);
        var boot = context.getBean(GameClientCommand.class);
//        boot.connectServer();
        SpringUtils.setContext(context);
        InputThread inputThread = new InputThread();
        inputThread.start();
    }

}
