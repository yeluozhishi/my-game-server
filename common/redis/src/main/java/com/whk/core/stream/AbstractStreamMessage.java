package com.whk.core.stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whk.core.message.AbstractRedisMessage;

/**
 * Redis Stream Message 抽象类
 *
 * @author 博承源码
 */
public abstract class AbstractStreamMessage extends AbstractRedisMessage {

    /**
     * 获得 Redis Stream Key
     *
     * @return Channel
     */
    @JsonIgnore // 避免序列化
    public abstract String getStreamKey();

}
