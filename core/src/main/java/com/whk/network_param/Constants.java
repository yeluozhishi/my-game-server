package com.whk.network_param;

public enum Constants {
    GAME_CENTER_PATH("WEB-CENTER"),
    GET_GATE_WAY("/user/getGameGateway");

    private String info;

    Constants(String info){
        this.info = info;
    }



}
