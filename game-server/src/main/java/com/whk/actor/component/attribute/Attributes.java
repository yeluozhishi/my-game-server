package com.whk.actor.component.attribute;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * 最终属性
 * 全部属性
 * 百分比属性
 * 全部重算：module计算出自己的属性，再相加到全部属性，最后更新至最终属性
 * 局部重算：module 计算出自己的属性值，旧值与新值比较，差值加到全部属性，最后变更部分更新至最终属性
 * <p>
 * 问题：属性怎么转id
 * 1 反射
 * 2 定义字典
 * 3 改为map
 */
@Getter
@Setter
public class Attributes {
    // 最终属性，供外部读取
    private Attribute finalAttribute = new Attribute();
    // 全部属性，只能自己使用
    private HashMap<String, Long> allAttribute = new HashMap<>();

}
