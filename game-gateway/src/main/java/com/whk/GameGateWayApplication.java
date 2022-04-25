package com.whk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GameGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GameGateWayApplication.class, args);
    }
}
