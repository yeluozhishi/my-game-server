package com.whk.dispatchprotocol;

import com.whk.SpringUtils;
import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.threadpool.handler.IQueueCommand;
import com.whk.threadpool.processor.ProcessorManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * 分发协议
 */
@Getter
@Slf4j
public class DispatchProtocolService {

    /**
     * 所有协议方法
     */
    private final HashMap<Integer, PlayerMessageRecord> methods = new HashMap<>();

    /**
     * 类名前缀
     */
    private final String CLASS_PRE = "handler";

    /**
     * 方法名前缀
     */
    private final String METHOD_PRE = "message";


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
                var list = Arrays.stream(value.getClass().getSuperclass().getDeclaredMethods())
                        .filter(f -> checkName(f.getName(), METHOD_PRE)).map(method -> {
                            var annotation = method.getAnnotation(HandlerDescription.class);
                            var messageId = getMessageId(method.getName());
                            return new PlayerMessageRecord(method, value, messageId, annotation.processorId());
                        }).toList();
                doRegister(list);
            }
        });
    }

    /**
     * 完成注册
     */
    private void doRegister(List<PlayerMessageRecord> methodsList) {
        for (PlayerMessageRecord record : methodsList) {
            if (record != null) {
                if (methods.containsKey(record.messageId())) {
                    throw new RuntimeException("协议号重复%d".formatted(record.messageId()));
                }
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
     * @param methodName 方法名
     * @return messageId
     */
    private int getMessageId(String methodName) {
        // 协议号
        return Integer.parseInt(methodName.split(METHOD_PRE)[1]);
    }


    public void dealMessage(int cmd, Function<PlayerMessageRecord, IQueueCommand> creator) {
        if (!methods.containsKey(cmd)) {
            log.error("没有该协议号:%d".formatted(cmd));
            return;
        }
        ProcessorManager.INSTANCE.process(creator.apply(methods.get(cmd)));
    }

}
