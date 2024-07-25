package com.whk.listener;

import lombok.Getter;

public enum EventEnum {
    LOGIN("登录"),
    LOGOUT("登出"),

    ;

    @Getter
    private final String description;

    EventEnum(String desc) {
        this.description = desc;
    }

}
