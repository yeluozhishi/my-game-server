package com.whk.module;

import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 物品存储
 */
@Getter
@Setter
public class Storage {

    // 基础:道具物品
    @Tag(1)
    private Map<Long, Item> items = new ConcurrentHashMap<>();

    // 基础:格子数
    @Tag(2)
    private int gridCount;

    // 基础:类型
    @Tag(3)
    private int type;

}
