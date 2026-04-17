package com.whk.client.service;

import cn.hutool.json.JSONUtil;
import com.whk.client.config.GameClientConfig;
import com.whk.client.entity.GameGatewayInfoMsg;
import com.whk.client.entity.UserInfo;
import com.whk.client.model.User;
import com.whk.client.model.UserMgr;
import com.whk.client.net.GameHttpClient;
import com.whk.constant.HttpConstants;
import com.whk.message.MapBean;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class GameClientInitService {

    private final GameClientConfig gameClientConfig;


    public GameClientInitService(GameClientConfig gameClientConfig) {
        this.gameClientConfig = gameClientConfig;
    }

    public boolean login(User user) {
        if (gameClientConfig.isUseGameCenter()){
            UserInfo param = new UserInfo();
            param.setUserName(user.getUserName());
            param.setPwd(user.getPwd());
            param.setZone(1);

            String uri = gameClientConfig.getGameCenterUrl() + HttpConstants.USER_LOGIN.getInfo();
            var re = GameHttpClient.post(uri, param);

            if (re == null){
                log.error("登录失败");
                return false;
            }

            var logInfo = JSONUtil.toBean(re, MapBean.class);
            var token = logInfo.get("token").toString();
            var gameGatewayInfo = (Map)logInfo.get("gameGatewayInfo");
            GameGatewayInfoMsg msg = new GameGatewayInfoMsg(gameGatewayInfo.get("ip").toString(), ((Number)gameGatewayInfo.get("port")).intValue(),
                    token, gameGatewayInfo.get("instanceId").toString(), ((Number)gameGatewayInfo.get("zone")).intValue());
            setGateAway(msg);

            user.setUserId(Long.parseLong(logInfo.get("id").toString()));
            user.setToken(token);
            UserMgr.getInstance().addUser(user);
        }
        return true;
    }

    public void showServerList(){
        String uri = gameClientConfig.getGameCenterUrl() + HttpConstants.SERVER_LIST.getInfo();
        var list = GameHttpClient.post(uri, MapBean.mapBean(Map.of("zone", gameClientConfig.getZone(), "open", true, "token", gameClientConfig.getToken())));
        log.info(list);
    }

    private void setGateAway(GameGatewayInfoMsg msg){
        gameClientConfig.setDefaultGameGatewayHost(msg.ip());
        gameClientConfig.setDefaultGameGatewayPort(msg.port());
        gameClientConfig.setToken(msg.token());
        gameClientConfig.setInstanceId(msg.instanceId());
        gameClientConfig.setZone(msg.zone());
        log.info("获取网关成功: " + msg);
    }

}
