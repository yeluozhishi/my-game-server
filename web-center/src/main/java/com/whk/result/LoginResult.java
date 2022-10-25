package com.whk.result;

import com.whk.game.GameGatewayService;

import java.util.Map;

public class LoginResult {
    private String id;
    private String token;
    private GameGatewayService.GameGatewayInfo gameGatewayInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GameGatewayService.GameGatewayInfo getGameGatewayInfo() {
        return gameGatewayInfo;
    }

    public void setGameGatewayInfo(GameGatewayService.GameGatewayInfo gameGatewayInfo) {
        this.gameGatewayInfo = gameGatewayInfo;
    }

    public Map toMap(){
        return Map.of("id", id, "token", token, "gameGatewayInfo", gameGatewayInfo);
    }
}
