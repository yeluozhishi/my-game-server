package com.whk.http;

import lombok.Getter;

@Getter
public enum HttpConstants {
    // SERVER 服务
    GAME_GATEWAY("http://GAME-GATEWAY"),

    UPDATE_SERVERS("/gate-server/updateServers"),
    ;

    private final String info;

    HttpConstants(String info){
        this.info = info;
    }

}