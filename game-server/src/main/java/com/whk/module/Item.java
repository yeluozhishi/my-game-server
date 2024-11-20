package com.whk.module;

import io.protostuff.Tag;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Item {
    @Tag(1)
    private Long uid;
    @Tag(2)
    private Long num;
    @Tag(3)
    private int itemId;
    // 随机属性
    @Tag(4)
    private List<AttrObject> randomAttrObject = new LinkedList<>();

}
