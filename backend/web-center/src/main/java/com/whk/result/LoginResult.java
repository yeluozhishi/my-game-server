package com.whk.result;

import com.whk.game.GameGatewayService;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class LoginResult {
    private Long userId;
    private String token;
    private GameGatewayService.GameGatewayInfo gameGatewayInfo;

    public Map toMap(){
        return Map.of("id", userId, "token", token, "gameGatewayInfo", gameGatewayInfo);
    }
}
