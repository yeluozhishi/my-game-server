package com.whk.network_param;

public enum Constants {
    GAME_CENTER_PATH("WEB-CENTER"),
    GET_GATE_WAY("/user/getGameGateway"),
    LOGIN("/user/login");

    private String info;

    Constants(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }

}
