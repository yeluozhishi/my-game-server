package com.whk.listener;

import com.whk.threadpool.ThreadPoolManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ApplicationListener implements CommandLineRunner, DisposableBean {

    private Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public void destroy() {
        logger.info("执行应用关闭");
        ThreadPoolManager.getInstance().closeThreadPool();
        logger.info("应用关闭完成");
    }

    @Override
    public void run(String... args) {
        logger.info("应用启动后加载");
        
        logger.info("加载结束");
    }
}
