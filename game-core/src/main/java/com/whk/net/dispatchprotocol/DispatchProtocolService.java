package com.whk.net.dispatchprotocol;

import com.whk.annotation.GameMessageHandler;
import org.apache.commons.lang3.math.NumberUtils;
import org.whk.SpringUtils;
import org.whk.message.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 分发协议
 */
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
     * 类名前缀
     */
    private final String CLASS_PRE = "handler";

    /**
     * 方法名前缀
     */
    private final String METHOD_PRE = "message";

    private final int SPILT_LENGTH = 2;

    /**
     * 类编号长度
     */
    public static int handlerSize = 100;

    /**
     * 方法编号长度
     */
    public static int messageSize = 100;

    private static DispatchProtocolService dispatchProtocolService;

    private DispatchProtocolService() {
        scannerClass();
        doRegister();
        clean();
    }

    public static DispatchProtocolService getInstance(){
        if (Objects.isNull(dispatchProtocolService)){
            dispatchProtocolService = new DispatchProtocolService();
        }
        return dispatchProtocolService;
    }

    /**
     * 获取拥有注解的类
     */
    private void scannerClass() {
        var beansWithAnnotation = SpringUtils.getBeansWithAnnotation(GameMessageHandler.class);
        beansWithAnnotation.forEach((key, value) -> {
            if (checkName(key, CLASS_PRE)) {
                var list = Arrays.stream(value.getClass().getDeclaredMethods())
                        .filter(f -> checkName(f.getName(), METHOD_PRE)).map(f -> {
                            try {
                                var instance = f.getDeclaringClass().getConstructors()[0].newInstance();
                                var messageId = getMessageId(key, f.getName());
                                return new InstanceHandlerRecord(f, instance, key, messageId);
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
        for (InstanceHandlerRecord record : methodsTemp) {
            if (record != null) {
                methods[record.messageId()] = record;
            }
        }
    }

    /**
     * 检查
     *
     * @param key 类名或方法名
     * @param pre 前缀
     * @return Boolean
     */
    private Boolean checkName(String key, String pre) {
        assert key != null;
        // 类名检查
        var name = key.split(pre);
        if (name.length != SPILT_LENGTH) {
            return false;
        }
        // 后缀为数字
        return NumberUtils.isDigits(name[1]);
    }


    /**
     * 获取消息id
     *
     * @param clazzName  类名
     * @param methodName 方法名
     * @return
     */
    private int getMessageId(String clazzName, String methodName) {
        // 协议号前面部分
        var pre = Integer.parseInt(clazzName.split(CLASS_PRE)[1]) * DispatchProtocolService.messageSize;
        // 协议号后面部分
        var end = Integer.parseInt(methodName.split(METHOD_PRE)[1]);
        return pre + end;
    }

    /**
     * 清理不必要数据
     */
    private void clean() {
        methodsTemp.clear();
    }

    public void dealMessage(Message message) throws InvocationTargetException, IllegalAccessException {
        var method = methods[message.getCommand()];
        if (method != null) {
            method.invoke(message);
        }
    }

}
