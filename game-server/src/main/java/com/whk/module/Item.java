package com.whk.module;

import io.protostuff.Tag;
import lombok.Data;

@Data
public class Item {
    @Tag(1)
    private Long uid;
    @Tag(2)
    private Long num;
    @Tag(3)
    private int itemId;
    @Tag(4)
    private ItemExtraInfo extraInfo;

}
