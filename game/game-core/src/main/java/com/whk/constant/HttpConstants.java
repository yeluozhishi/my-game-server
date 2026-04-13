package com.whk.constant;

import lombok.Getter;

@Getter
public enum HttpConstants {
    // SERVER 服务
    WEB_CENTER("http://WEB-CENTER"),

    CLIENT_GET_GATE_WAY("/user/getGameGateway"),

    USER_LOGIN("/user/login"),

    USER_CREATE_PLAYER("/user/createPlayer"),

    USER_GET_PLAYERS("/user/getPlayers"),



    SERVER_LIST("/server/list"),
    ;

    private final String info;

    HttpConstants(String info){
        this.info = info;
    }

}
