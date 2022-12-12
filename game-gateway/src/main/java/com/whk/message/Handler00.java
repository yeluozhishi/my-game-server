package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.constant.HttpConstants;
import com.whk.net.enity.MapBean;
import com.whk.net.enity.Message;
import com.whk.net.http.HttpClient;
import com.whk.rpc.api.IRpcPlayerBase;
import com.whk.net.RpcGateProxyHolder;
import com.whk.user.UserMgr;
import com.whk.util.GsonUtil;

import java.util.Map;

@GameMessageHandler
public class Handler00 {

    public void message00(Message message){
        var userName = message.getBody().getString("userName");
        var user = UserMgr.INSTANCE.getUserByUsernameWithoutCheck(userName);
        user.ifPresent( u -> {
            var serverId = u.getServerId();
            var playerBase = RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId).getPlayers(userName);
            u.sendToClientMessage(new Message(0x0000, "", new MapBean(Map.of("l", playerBase))));
        });

    }

    /**
     * 角色创建
     * @param message
     */
    public void message01(Message message){
        var userName = message.getBody().getString("userName");
        // 获取playerId
        Map body = Map.of("userName", userName, "sex", 1, "kind", 0, "token", HttpClient.getToken());
        var re = HttpClient.getRestTemplate().postForObject(HttpConstants.WEB_CENTER.getHttpAndInfo() + HttpConstants.USER_CREATE_PLAYER.getInfo(),
                body, String.class);
        var map = GsonUtil.INSTANCE.<String>GsonToMaps(re);
        var pid = map.getOrDefault("pid", "");
        if (!pid.isBlank()){


        }


    }

    /**
     * 角色登录
     * @param message
     */
    public void message02(Message message){
        var playerId = message.getPlayerId();
        var userId = message.getBody().getString("userId");
        UserMgr.INSTANCE.playerLogin(playerId, userId);

    }


}
