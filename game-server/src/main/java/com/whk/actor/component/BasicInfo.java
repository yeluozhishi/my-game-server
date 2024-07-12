package com.whk.actor.component;

import io.protostuff.Tag;
import lombok.Data;

@Data
public class BasicInfo {

    /**
     * 名字
     */
    @Tag(1)
    private String name;

    /**
     * 性别
     */
    @Tag(2)
    private int sex;

    /**
     * 职业
     */
    @Tag(3)
    private int career;
}
