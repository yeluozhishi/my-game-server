package com.whk.net.dispatchprotocol;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.ThreadAssign;
import com.whk.threadpool.MessageProcessor;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.event.EventFactory;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whk.SpringUtils;

import org.whk.protobuf.message.MessageWrapperOuterClass;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 分发协议
 */
public class DispatchProtocolService {

    private static final Logger log = LoggerFactory.getLogger(DispatchProtocolService.class);
    /**
     * 所有协议方法
     */
    private HashMap<Integer, MessageHandlerRecord> methods;

    /**
     * 临时保存所有相关的服务类
     */
    private final List<MessageHandlerRecord> methodsTemp = new LinkedList<>();

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
                        .filter(f -> checkName(f.getName(), METHOD_PRE)).map(method -> {
                            var messageId = getMessageId(key, method.getName());
                            try {
                                var instance = method.getDeclaringClass().getConstructors()[0].newInstance();
                                var th = method.getAnnotation(ThreadAssign.class) ;
                                ThreadPoolManager manager = (th == null) ? ThreadPoolManager.PLAYER_THREAD : th.value();
                                return new MessageHandlerRecord(method, instance, manager, messageId);
                            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                                log.error("方法注册错误{}", messageId);
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
        methods = new HashMap<>();
        for (MessageHandlerRecord record : methodsTemp) {
            if (record != null) {
                methods.put(record.messageId(), record);
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

    public void dealMessage(MessageWrapperOuterClass.MessageWrapper message) throws Exception {
        var method = methods.get(message.getMessage().getCommand());
        if (method != null) {
            MessageProcessor.INSTANCE.addEvent(message.getPlayerId(), EventFactory.INSTANCE.create(message, method));
        }
    }

}
