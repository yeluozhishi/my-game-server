package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DispatchGameMessageService {

    // 所有协议方法
    private Method[] methods;

    //临时保存所有相关的服务类
    private Map<String, List<Method>> methodsTemp = new HashMap<>();

    // 上下文
    private ApplicationContext applicationContext;

    // 类名前缀
    private String classPre = "handler";
    // 方法名前缀
    private String methodPre = "message";
    // 类编号长度
    public static int handlerSize = 100;
    // 方法编号长度
    public static int messageSize = 100;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        scannerClass();
        doRegister();
        clean();
    }


    /**
     * 获取拥有注解的类
     */
    private void scannerClass() {
        var beansWithAnnotation = applicationContext.getBeansWithAnnotation(GameMessageHandler.class);
        beansWithAnnotation.forEach((key, value) -> {
            if (checkName(key, classPre)){
                var list = Arrays.stream(value.getClass().getDeclaredMethods())
                        .filter(f -> checkName(f.getName(), methodPre)).toList();
                methodsTemp.put(key.split("_")[1], list);
            }
        });
    }

    /**
     * 完成注册
     */
    private void doRegister() {
        methods = new Method[handlerSize * messageSize];
        try {
            methodsTemp.forEach((key, values) -> {
                // 协议号前面部分
                var pre = Integer.parseInt(key) * messageSize;
                values.forEach(method -> {
                    // 协议号后面部分
                    var end = Integer.parseInt(method.getName().split("_")[1]);
                    methods[pre + end] = method;
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean checkName(String key, String pre){
        assert key != null;
        // 类名检查
        var name = key.split("_");
        if (name.length != 2){
            return false;
        }
        if (!name[0].equals(pre)){
            return false;
        }
        // 后缀为数字
        Integer.parseInt(name[1]);
        return true;
    }

    /**
     * 清理不必要数据
     */
    private void clean(){
        methodsTemp.clear();
    }

    public Method getMethod(int id){
        return methods[id];
    }



}
