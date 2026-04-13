package com.whk.listener.eventlistener;

import lombok.Getter;

@Getter
public enum EventEnum {
    LOGIN("登录"),
    LOGOUT("登出"),

    ;

    private final String description;

    EventEnum(String desc) {
        this.description = desc;
    }

}
