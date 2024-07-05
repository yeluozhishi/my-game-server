package com.whk.threadpool.dispatchprotocol;

import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.ThreadAssign;
import com.whk.threadpool.MessageProcessor;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.event.EventCreator;
import org.apache.commons.lang3.math.NumberUtils;
import com.whk.SpringUtils;
import com.whk.protobuf.message.MessageProto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 分发协议
 */
public class DispatchProtocolService {

    /**
     * 所有协议方法
     */
    private final HashMap<Integer, MessageHandlerRecord> methods = new HashMap<>();

    /**
     * 类名前缀
     */
    private final String CLASS_PRE = "handler";

    /**
     * 方法名前缀
     */
    private final String METHOD_PRE = "message";


    /**
     * 方法编号长度
     */
    public static int messageSize = 100;


    public DispatchProtocolService() {
        scannerClass();
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
                            var th = method.getAnnotation(ThreadAssign.class) ;
                            ThreadPoolExecutor threadPoolExecutor = (th == null) ? ThreadPoolManager.getInstance().getPlayerThread() : th.value().getExecutor();
                            return new MessageHandlerRecord(method, value, threadPoolExecutor, messageId);
                        }).toList();
                doRegister(list);
            }
        });
    }

    /**
     * 完成注册
     */
    private void doRegister( List<MessageHandlerRecord> methodsList) {
        for (MessageHandlerRecord record : methodsList) {
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
        if (name.length != 2) {
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
     * @return messageId
     */
    private int getMessageId(String clazzName, String methodName) {
        // 协议号前面部分
        var pre = Integer.parseInt(clazzName.split(CLASS_PRE)[1]) * DispatchProtocolService.messageSize;
        // 协议号后面部分
        var end = Integer.parseInt(methodName.split(METHOD_PRE)[1]);
        return pre + end;
    }


    public void dealMessage(MessageProto.Message message, long id, EventCreator creator) {
        var method = methods.get(message.getCommand());
        if (method != null) {
            MessageProcessor.INSTANCE.addEvent(id, creator.create(method));
        }
    }

}
