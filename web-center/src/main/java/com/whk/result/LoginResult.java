package com.whk.result;

import com.whk.game.GameGatewayService;

import java.util.Map;

public class LoginResult {
    private Long userId;
    private String token;
    private GameGatewayService.GameGatewayInfo gameGatewayInfo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return Map.of("id", userId, "token", token, "gameGatewayInfo", gameGatewayInfo);
    }
}
