package com.whk.message;

import com.netflix.servo.util.Reflection;
import com.whk.annotation.GameMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DispatchGameMessageService {

    private Method[] methods;

    //保存所有相关的服务类
    private final List<String> classNames = new ArrayList<>();

    private ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init(){
        scannerClass();
        doRegister();
    }


    /**
     * 递归扫描类文件
     */
    private void scannerClass(){
        var beansWithAnnotation = applicationContext.getBeansWithAnnotation(GameMessageHandler.class);
        System.out.println("1");

        beansWithAnnotation.keySet().forEach(f -> {
            classNames.add(f);
        });

    }

    /**
     * 完成注册
     */
    private void doRegister(){
        if(classNames.size() == 0){ return; }
        HashMap<Integer, Method[]> map = new HashMap<Integer, Method[]>();
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                var name = clazz.getName();
                var methods = clazz.getDeclaredMethods();
                System.out.println("1");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        classNames.clear();
    }


}
