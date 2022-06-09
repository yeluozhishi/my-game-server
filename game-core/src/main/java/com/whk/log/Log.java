package com.whk.log;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.logging.LogManager;

/**
 *
 */
@Service
public class Log {
    private static final LogManager logManager = LogManager.getLogManager();

    public Log() {
        try {
            // 获取配置文件的输入流, 配置文件放在src下
            InputStream input = Log.class
                    .getClassLoader()
                    .getResourceAsStream("log.properties");
            // 读取配置输入流
            logManager.readConfiguration(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    }
}
