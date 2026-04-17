package com.whk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
@EnableScheduling
public class WebCenterApplication {

    public static void main(String[] args) {
        String s = "SELECT TABLE_NAME, COLUMN_NAME, DATA_TYPE, COLUMN_KEY, EXTRA, COLLATION_NAME, ORDINAL_POSITION, COLUMN_TYPE, IS_NULLABLE FROM information_schema.columns WHERE TABLE_SCHEMA='admin' AND TABLE_NAME IN ('sys_user','user_account','player_info','server_info') ORDER BY ORDINAL_POSITION";
        SpringApplication.run(WebCenterApplication.class, args);
    }
}
