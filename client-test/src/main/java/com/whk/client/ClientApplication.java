package com.whk.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;


@SpringBootApplication(scanBasePackages = {"com.whk"})
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ClientApplication.class);
        // 关闭web服务
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}
