package com.whk.constant;

public enum Constants {
    // SERVER 服务
    WEB_CENTER("WEB-CENTER"),

    // client
    CLIENT_GET_GATE_WAY("/user/getGameGateway"),
    CLIENT_LOGIN("/user/login"),

    //gate
    GATE_GET_SERVER_LIST("/server/server_list");

    private final String info;

    Constants(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }

}
