package com.whk.client.service;

import com.whk.client.component.GameClientCommand;
import com.whk.client.config.GameClientConfig;
import com.whk.client.entity.GameGatewayInfoMsg;
import com.whk.client.entity.SelectGameGatewayParam;
import com.whk.client.model.User;
import com.whk.http.GameHttpClient;
import com.whk.constant.Constants;
import com.whk.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Configuration
public class GameClientInitService {
    private Logger logger = Logger.getLogger(GameClientInitService.class.getName());

    private GameClientConfig gameClientConfig;

    private GameClientCommand clientCommand;

    @Autowired
    public void setClientCommand(GameClientCommand clientCommand) {
        this.clientCommand = clientCommand;
    }

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
            User user = new User();
            param.setUser_name(user.getUser());
            param.setPwd(user.getPwd());

            var msg = this.selectGateway(param, user);
            if (msg.isPresent()){
                var m = msg.get();
                gameClientConfig.setDefaultGameGatewayHost(m.ip());
                gameClientConfig.setDefaultGameGatewayPort(m.port());
                gameClientConfig.setToken(m.token());
                gameClientConfig.setInstanceId(m.instanceId());
                logger.info("获取网关成功");
            } else {
                logger.severe("获取网关失败");
            }
        }
    }

    public Optional<GameGatewayInfoMsg> selectGateway(SelectGameGatewayParam param, User user){
        String uri = gameClientConfig.getGameCenterUrl() + Constants.WEB_CENTER.getInfo() + Constants.CLIENT_GET_GATE_WAY.getInfo();
        var re = login(param);

        if (re == null){
            logger.severe("登录失败");
            return Optional.empty();
        }
        var logInfo = (Map)GsonUtil.INSTANCE.GsonToMaps(re).get("data");
        var token = logInfo.get("token").toString();
        var gameGatewayInfo = (Map)logInfo.get("gameGatewayInfo");
        GameGatewayInfoMsg msg = new GameGatewayInfoMsg(gameGatewayInfo.get("ip").toString(), ((Number)gameGatewayInfo.get("port")).intValue(),
                token, gameGatewayInfo.get("instanceId").toString());
        user.setToken(token);
        clientCommand.setUser(user);
        return Optional.ofNullable(msg);
    }

    public String login(SelectGameGatewayParam param){
        String uri = gameClientConfig.getGameCenterUrl() + Constants.WEB_CENTER.getInfo() + Constants.CLIENT_LOGIN.getInfo();
        return GameHttpClient.post(uri, param);
    }
}
