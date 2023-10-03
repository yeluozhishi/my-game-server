package com.whk;

import lombok.Data;

@Data
public class ShortMessageBox {
    // 编号
    private int code;
    // 文本
    private String msg;
    // 颜色
    private String color;
    // 行为
    private int behavior;
}
