package com.whk.net.dispatchprotocol;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.enity.Message;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 分发协议
 */
@Service
public class DispatchProtocolService {

    /**
     * 所有协议方法
     */
    private InstanceHandlerRecord[] methods;

    /**
     * 临时保存所有相关的服务类
     */
    private final List<InstanceHandlerRecord> methodsTemp = new LinkedList<>();

    /**
     * 上下文
     */
    private ApplicationContext applicationContext;

    /**
     * 类名前缀
     */
    private final String classPre = "handler";

    /**
     * 方法名前缀
     */
    private final String methodPre = "message";

    private final int SPILT_LENGTH = 2;

    /**
     * 类编号长度
     */
    public static int handlerSize = 100;

    /**
     * 方法编号长度
     */
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
                        .filter(f -> checkName(f.getName(), methodPre)).map(f -> {
                            try {
                                return new InstanceHandlerRecord(f, f.getDeclaringClass().getConstructors()[0].newInstance(), key);
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }).toList();
                methodsTemp.addAll(list);
            }
        });
    }

    /**
     * 完成注册
     */
    private void doRegister() {
        methods = new InstanceHandlerRecord[handlerSize * messageSize];
        methodsTemp.stream().filter(Objects::nonNull).forEach(record -> methods[record.getMessageId()] = record);
    }

    /**
     * 检查
     * @param key 类名
     * @param pre 前缀
     * @return Boolean
     */
    private Boolean checkName(String key, String pre){
        assert key != null;
        // 类名检查
        var name = key.split("_");
        if (name.length != SPILT_LENGTH){
            return false;
        }
        if (!name[0].equals(pre)){
            return false;
        }
        // 后缀为数字
        assert NumberUtils.isDigits(name[1]);
        return true;
    }

    /**
     * 清理不必要数据
     */
    private void clean(){
        methodsTemp.clear();
    }

    public void dealMessage(Message message) throws InvocationTargetException, IllegalAccessException {
        var method = methods[message.getCommand()];
        if (method != null){
            method.invoke(message);
        }
    }

}
