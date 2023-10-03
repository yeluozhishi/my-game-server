package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.rpc.api.IRpcPlayerBase;
import com.whk.net.RpcGateProxyHolder;
import com.whk.user.UserMgr;

import org.whk.protobuf.message.MessageWrapperOuterClass;
import org.whk.protobuf.message.PlayerInfoOuterClass;

import java.io.IOException;

@GameMessageHandler
public class Handler00 {

    /**
     * 获取角色列表
     * @param message
     */
    public void message00(MessageWrapperOuterClass.MessageWrapper message) {
        var user = UserMgr.INSTANCE.getUserByUserIdWithoutCheck(message.getUserId());
        user.ifPresent(u -> {
            var serverId = u.getServerId();
            var playerBase = RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId).getPlayers(u.getUserId());
            var builder = PlayerInfoOuterClass.PlayerInfos.newBuilder();
            for (var playerEntity : playerBase) {
                var playerInfo = PlayerInfoOuterClass.PlayerInfo.newBuilder().setId(playerEntity.getId())
                        .setCareer(playerEntity.getCareer()).setSex(playerEntity.getSex())
                        .setUserId(playerEntity.getUserAccountId()).setLastLogin(playerEntity.getLastLogin()).build();
                builder.addPlayerInfos(playerInfo);
            }
            u.sendToClientMessage(message.getMessage().toBuilder().clearBody().setPlayerInfos(builder.build()).build());
        });

    }

    /**
     * 角色创建
     *
     * @param message
     */
    public void message01(MessageWrapperOuterClass.MessageWrapper message) throws IOException {
//        var userName = message.getBody().getString("userName");
//        var userId = message.getBody().getLong("userId");
//        var serverId = message.getBody().getInt("serverId");
//        // 获取playerId
//        Map body = Map.of("userName", userName, "sex", 1, "kind", 0, "token", HttpClient.getToken());
//        var re = HttpClient.getRestTemplate().postForObject(HttpConstants.WEB_CENTER.getHttpAndInfo() + HttpConstants.USER_CREATE_PLAYER.getInfo(),
//                body, String.class);
//        var map = GsonUtil.INSTANCE.<String>GsonToMaps(re);
//        var pid = Long.parseLong(map.getOrDefault("pid", "0"));
//        if (pid != 0) {
//            var result = RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId)
//                    .createPlayer(userId, userName, RpcGateProxyHolder.getInstanceId(), pid);
//
//            if (result) {
//                UserMgr.INSTANCE.playerLogin(pid, userName);
//                UserMgr.INSTANCE.getUserByUsername(userName)
//                        .ifPresent(u -> u.sendToClientMessage(new MessageOuterClass.Message(0x0001, u.getPlayerId(), new MapBean(Map.of("pid", pid)))));
//            }
//        }


    }

    /**
     * 角色登录
     *
     * @param message
     */
    public void message02(MessageWrapperOuterClass.MessageWrapper message) throws IOException {
//        var userName = message.getLoginReq().getUserName();
//        var userId = message.getLoginReq().("userId");
//        UserMgr.INSTANCE.playerLogin(playerId, userName);
//        var user = UserMgr.INSTANCE.getUserByPlayerId(playerId);
//        if (user.isPresent()){
//            RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, user.get().getServerId())
//                    .createPlayer(userId, userName, RpcGateProxyHolder.getInstanceId(), playerId);
//        }
    }

    /**
     * 测试消息
     *
     * @param message
     */
    public void message03(MessageWrapperOuterClass.MessageWrapper message) throws IOException {
        System.out.println(message);
    }


}
