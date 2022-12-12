package com.whk.constant;

public enum HttpConstants {
    // SERVER 服务
    HTTP("http://"),

    WEB_CENTER("WEB-CENTER"),

    CLIENT_GET_GATE_WAY("/user/getGameGateway"),
    USER_LOGIN("/user/login"),

    SERVER_LIST("/server/list"),

    USER_CREATE_PLAYER("/user/createPlayer");

    private final String info;

    HttpConstants(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }

    public String getHttpAndInfo(){
        return HTTP.info + this.info;
    }

}
