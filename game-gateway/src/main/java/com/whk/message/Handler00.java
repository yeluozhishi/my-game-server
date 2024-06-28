package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.http.HttpClient;
import com.whk.rpc.api.IRpcPlayerBase;
import com.whk.net.RpcGateProxyHolder;
import com.whk.user.UserMgr;

import org.whk.GsonUtil;
import org.springframework.transaction.annotation.Transactional;
import org.whk.message.PlayerEntityMessage;
import org.whk.message.ReqCreatePlayerMessage;
import org.whk.message.ReqPlayerListMessage;
import org.whk.protobuf.message.MessageProto;
import org.whk.protobuf.message.PlayerInfoProto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@GameMessageHandler
public class Handler00 {

    /**
     * 用户登录
     *
     * @param message
     */
    public void message00(MessageProto.Message message, long userId) {
    }

    /**
     * 角色创建
     *
     * @param message
     */
    @Transactional
    public void message01(MessageProto.Message message, long userId) throws IOException {
        var serverId = message.getCreatePlayer().getServerId();
        var sex = message.getCreatePlayer().getSex();
        var kind = message.getCreatePlayer().getKind();
        // 获取playerId
        ReqCreatePlayerMessage reqCreatePlayerMessage = new ReqCreatePlayerMessage();
        reqCreatePlayerMessage.setKind(kind);
        reqCreatePlayerMessage.setSex(sex);
        reqCreatePlayerMessage.setUserId(userId);
        var re = HttpClient.getInstance().createPlayer(reqCreatePlayerMessage, String.class);

        var map = GsonUtil.INSTANCE.gsonToMaps(re);
        var pid = ((Double) map.get("pid")).longValue();
        if (pid != 0) {
            RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId)
                    .createPlayer(RpcGateProxyHolder.getInstanceId(), pid);
            UserMgr.INSTANCE.playerLogin(pid);
        }
        UserMgr.INSTANCE.getUserByUserId(userId);
    }

    /**
     * 角色登录
     *
     * @param message
     */
    public void message02(MessageProto.Message message, long userId) {
        var playerId = message.getReqPlayerLogin().getPlayerId();
        UserMgr.INSTANCE.playerLogin(playerId);
        var user = UserMgr.INSTANCE.getUserByPlayerId(playerId);
        user.ifPresent(value -> RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, value.getServerId())
                .playerLogin(userId, RpcGateProxyHolder.getInstanceId(), playerId));
    }

    /**
     * 测试消息
     *
     * @param message
     */
    public void message03(MessageProto.Message message, long userId) {
        RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, 1)
                .test("hello");
        System.out.println(message);
    }

    /**
     * 获取角色列表
     *
     * @param message
     */
    public void message04(MessageProto.Message message, long userId) {
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        ReqPlayerListMessage playerListMessage = new ReqPlayerListMessage();
        playerListMessage.setUserId(userId);
        List<PlayerEntityMessage> players = HttpClient.getInstance().getPlayerList(playerListMessage, PlayerEntityMessage.class);


        var serverId = user.getServerId();
        var playerBase = RpcGateProxyHolder.<IRpcPlayerBase>getInstance(IRpcPlayerBase.class, serverId).getPlayers(user.getUserId());
        var builder = PlayerInfoProto.PlayerInfos.newBuilder();
        for (var playerEntity : playerBase) {
            var playerInfo = PlayerInfoProto.PlayerInfo.newBuilder().setId(playerEntity.getId())
                    .setCareer(playerEntity.getCareer()).setSex(playerEntity.getSex())
                    .setUserId(playerEntity.getUserAccountId()).setLastLogin(playerEntity.getLastLogin()).build();
            builder.addPlayerInfos(playerInfo);
        }
        user.sendToClientMessage(message.toBuilder().clearBody().setPlayerInfos(builder.build()).build());

    }

    /**
     * tips
     *
     * @param message
     */
    public void message05(MessageProto.Message message, long userId) {
    }
}
