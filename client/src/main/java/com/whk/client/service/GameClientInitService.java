package com.whk.client.service;

import com.whk.client.config.GameClientConfig;
import com.whk.client.entity.SelectGameGatewayParam;
import com.whk.client.entity.GameGatewayInfoMsg;
import com.whk.http.GameHttpClient;
import com.whk.network_param.Constants;
import com.whk.util.GsonUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.logging.Logger;

public class GameClientInitService {
    private Logger logger = Logger.getLogger(GameClientInitService.class.getName());

    private GameClientConfig gameClientConfig;

    @Autowired
    public void setGameClientConfig(GameClientConfig gameClientConfig) {
        this.gameClientConfig = gameClientConfig;
    }

    @PostConstruct
    public void init(){
        this.selectGateway();
    }

    public void selectGateway(){
        if (gameClientConfig.isUseGameCenter()){
            SelectGameGatewayParam param = new SelectGameGatewayParam();
            param.setUserName("whk");
            param.setPwd("123");

            var msg = this.selectGateway(param);
            if (msg.isPresent()){
                var m = msg.get();
                gameClientConfig.setDefaultGameGatewayHost(m.getIp());
                gameClientConfig.setDefaultGameGatewayPort(m.getPort());
                gameClientConfig.setToken(m.getToken());
                logger.severe("获取网关成功");
            } else {
                logger.severe("获取网关失败");
            }
        }
    }

    public Optional<GameGatewayInfoMsg> selectGateway(SelectGameGatewayParam param){
        String uri = gameClientConfig.getGameCenterUrl() + Constants.GAME_CENTER_PATH + Constants.GET_GATE_WAY;
        String response = GameHttpClient.post(uri, param);
        if (param == null){
            logger.severe("获取游戏网关失败");
            return Optional.empty();
        }
        return Optional.ofNullable(GsonUtil.INSTANCE.GsonToBean(response, GameGatewayInfoMsg.class));
    }
}
