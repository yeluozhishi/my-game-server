package com.whk.match.id;

import lombok.Getter;

@Getter
public enum IDConst {
    MAP(0, "地图"),

    ROLE(1, "角色"),

    ITEM(2, "道具"),

    CACHE(3, "杂项"),

    CHAT(4, "聊天")
    ;

    private final int id;
    private final String desc;

    IDConst(int id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
