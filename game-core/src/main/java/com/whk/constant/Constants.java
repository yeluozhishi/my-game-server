package com.whk.constant;

public enum Constants {
    // SERVER 服务
    HTTP("http://"),
    WEB_CENTER("WEB-CENTER"),

    // client
    CLIENT_GET_GATE_WAY("/user/getGameGateway"),
    CLIENT_LOGIN("/user/login"),

    //gate
    SERVER_LIST("/server/list");

    private final String info;

    Constants(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }

    public String getHttpAndInfo(){
        return HTTP.info + this.info;
    }

}
