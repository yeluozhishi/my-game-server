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

    public void message00(Message message) {
        var userName = message.getBody().getString("userName");
        var user = UserMgr.INSTANCE.getUserByUsernameWithoutCheck(userName);
        user.ifPresent(u -> {
            var serverId = u.getServerId();
            var map = new MapBean();
//            RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId).test(userName);
            var playerBase = RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId).getPlayers(userName);
            map.put("l", playerBase);
            u.sendToClientMessage(new Message(0x0000, "", map));
        });

    }

    /**
     * 角色创建
     *
     * @param message
     */
    public void message01(Message message) {
        var userName = message.getBody().getString("userName");
        var serverId = message.getBody().getInt("serverId");
        // 获取playerId
        Map body = Map.of("userName", userName, "sex", 1, "kind", 0, "token", HttpClient.getToken());
        var re = HttpClient.getRestTemplate().postForObject(HttpConstants.WEB_CENTER.getHttpAndInfo() + HttpConstants.USER_CREATE_PLAYER.getInfo(),
                body, String.class);
        var map = GsonUtil.INSTANCE.<String>GsonToMaps(re);
        var pid = map.getOrDefault("pid", "");
        if (!pid.isBlank()) {
            var result = RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId)
                    .createPlayer(userName, RpcGateProxyHolder.getInstanceId(), pid);

            if (result) {
                UserMgr.INSTANCE.playerLogin(pid, userName);
                var user = UserMgr.INSTANCE.getUserByUsername(userName);
                user.ifPresent(u -> u.sendToClientMessage(new Message(0x0001, u.getPlayerId(), new MapBean(Map.of("pid", pid)))));
            }
        }


    }

    /**
     * 角色登录
     *
     * @param message
     */
    public void message02(Message message) {
        var playerId = message.getPlayerId();
        var userName = message.getBody().getString("userName");
        UserMgr.INSTANCE.playerLogin(playerId, userName);
        var user = UserMgr.INSTANCE.getUserByPlayerId(playerId);
        user.ifPresent(u -> RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, u.getServerId())
                .createPlayer(userName, RpcGateProxyHolder.getInstanceId(), playerId));
    }


}
