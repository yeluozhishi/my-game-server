package com.whk.client.service;

import com.whk.client.component.GameClientCommand;
import com.whk.client.config.GameClientConfig;
import com.whk.client.entity.GameGatewayInfoMsg;
import com.whk.client.entity.UserInfo;
import com.whk.client.model.User;
import com.whk.client.model.UserMgr;
import com.whk.client.net.GameHttpClient;
import com.whk.constant.HttpConstants;
import org.whk.GsonUtil;
import org.whk.message.MapBean;

import java.util.Map;
import java.util.logging.Logger;

public class GameClientInitService {
    private final Logger logger = Logger.getLogger(GameClientInitService.class.getName());

    private final GameClientConfig gameClientConfig;

    private final GameClientCommand clientCommand;

    public GameClientInitService(GameClientConfig gameClientConfig, GameClientCommand clientCommand) {
        this.gameClientConfig = gameClientConfig;
        this.clientCommand = clientCommand;
    }

    public void login() {
        if (gameClientConfig.isUseGameCenter()){
            UserInfo param = new UserInfo();
            User user = new User();
            param.setUserName(user.getUserName());
            param.setPwd(user.getPwd());
            param.setZone(1);

            var re = sendMsg(param);

            if (re == null){
                logger.severe("登录失败");
                return;
            }

            var logInfo = (Map)GsonUtil.INSTANCE.gsonToMaps(re);
            var token = logInfo.get("token").toString();
            var gameGatewayInfo = (Map)logInfo.get("gameGatewayInfo");
            GameGatewayInfoMsg msg = new GameGatewayInfoMsg(gameGatewayInfo.get("ip").toString(), ((Number)gameGatewayInfo.get("port")).intValue(),
                    token, gameGatewayInfo.get("instanceId").toString(), ((Number)gameGatewayInfo.get("zone")).intValue());
            setGateAway(msg);

            user.setToken(token);
            UserMgr.init(user);
        }
    }

    public void showServerList(){
        String uri = gameClientConfig.getGameCenterUrl() + HttpConstants.WEB_CENTER.getInfo() + HttpConstants.SERVER_LIST.getInfo();
        var list = GameHttpClient.post(uri, MapBean.mapBean(Map.of("zone", gameClientConfig.getZone(), "token", gameClientConfig.getToken())));
        System.out.println(list);
    }

    private void setGateAway(GameGatewayInfoMsg msg){
        gameClientConfig.setDefaultGameGatewayHost(msg.ip());
        gameClientConfig.setDefaultGameGatewayPort(msg.port());
        gameClientConfig.setToken(msg.token());
        gameClientConfig.setInstanceId(msg.instanceId());
        gameClientConfig.setZone(msg.zone());
        logger.info("获取网关成功: " + msg);
    }

    private String sendMsg(UserInfo param){
        String uri = gameClientConfig.getGameCenterUrl() + HttpConstants.WEB_CENTER.getInfo() + HttpConstants.USER_LOGIN.getInfo();
        return GameHttpClient.post(uri, param);
    }
}
