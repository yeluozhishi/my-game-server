package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.constant.HttpConstants;
import com.whk.net.http.HttpClient;
import com.whk.rpc.api.IRpcPlayerBase;
import com.whk.net.RpcGateProxyHolder;
import com.whk.user.UserMgr;

import com.whk.util.GsonUtil;
import org.springframework.transaction.annotation.Transactional;
import org.whk.protobuf.message.MessageWrapperOuterClass;
import org.whk.protobuf.message.PlayerInfoOuterClass;

import java.io.IOException;
import java.util.Map;

@GameMessageHandler
public class Handler00 {

    /**
     * 用户登录
     * @param message
     */
    public void message00(MessageWrapperOuterClass.MessageWrapper message) {
    }

    /**
     * 角色创建
     *
     * @param message
     */
    @Transactional
    public void message01(MessageWrapperOuterClass.MessageWrapper message) throws IOException {
        var userId = message.getMessage().getCreatePlayer().getUserId();
        var serverId = message.getMessage().getCreatePlayer().getServerId();
        var sex = message.getMessage().getCreatePlayer().getSex();
        var kind = message.getMessage().getCreatePlayer().getKind();
        // 获取playerId
        Map body = Map.of("userId", userId, "sex", sex, "kind", kind, "token", HttpClient.getToken());
        var re = HttpClient.getRestTemplate().postForObject(HttpConstants.WEB_CENTER.getHttpAndInfo() + HttpConstants.USER_CREATE_PLAYER.getInfo(),
                body, String.class);
        var map = GsonUtil.INSTANCE.GsonToMaps(re);
        var pid = ((Double) map.get("pid")).longValue();
        if (pid != 0) {
            var result = RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId)
                    .createPlayer(userId, RpcGateProxyHolder.getInstanceId(), pid);

            if (result) {
                UserMgr.INSTANCE.playerLogin(pid);
            }
        }


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
        RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, 1)
                .test("hello");
        System.out.println(message);
    }

    /**
     * 获取角色列表
     * @param message
     */
    public void message04(MessageWrapperOuterClass.MessageWrapper message) {
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
}
