package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.enity.MapBean;
import com.whk.net.enity.Message;
import com.whk.rpc.api.IRpcPlayerBase;
import com.whk.service.RpcGateProxyHolder;
import com.whk.user.UserMgr;

import java.util.ArrayList;
import java.util.Map;

@GameMessageHandler
public class Handler00 {

    public void message00(Message message){
        var userName = message.getBody().getString("userId");
        var user = UserMgr.INSTANCE.getUserByUsernameWithoutCheck(userName);
        user.ifPresent( u -> {
            var serverId = u.getServerId();
            var playerBase = RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId).getPlayers(userName);
            u.sendToClientMessage(new Message(0x0000, "", new MapBean(Map.of("l", new ArrayList()))));
        });

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
