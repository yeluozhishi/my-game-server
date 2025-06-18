package com.whk.message;

import lombok.Data;

@Data
public class ShortMessageBox {
    // 编号
    private int code;
    // 文本
    private String[] msg;
}
