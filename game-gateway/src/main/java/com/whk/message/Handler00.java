package com.whk.message;

import com.whk.annotation.GameMessageHandler;
import com.whk.net.RpcGateProxyHolder;
import com.whk.net.http.HttpClient;
import com.whk.net.rpc.api.IRpcPlayerBase;
import com.whk.net.rpc.serialize.wrapper.ListWrapper;
import com.whk.user.User;
import com.whk.user.UserMgr;
import org.springframework.transaction.annotation.Transactional;
import com.whk.GsonUtil;
import com.whk.protobuf.message.MessageProto;
import com.whk.protobuf.message.PlayerInfoProto;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;

@GameMessageHandler
public class Handler00 {

    /**
     * 用户登录
     *
     */
    public void message00(MessageProto.Message message, long userId) {
        System.out.printf("userId  %d  已登录。%n", userId);
    }

    /**
     * 角色创建
     *
     */
    @Transactional
    public void message01(MessageProto.Message message, long userId) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
        User user = UserMgr.INSTANCE.getUserByUserId(userId);
        if (pid != 0) {
            RpcGateProxyHolder.getInstance(IRpcPlayerBase.class, serverId)
                    .createPlayer(RpcGateProxyHolder.gateTopic(), pid);

            if (!UserMgr.INSTANCE.playerLogin(userId, pid)){
                user.sendTips(19);
                return;
            }
        }
        user.sendTips(18);
    }

    /**
     * 角色登录
     *
     */
    public void message02(MessageProto.Message message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playerId = message.getReqPlayerLogin().getPlayerId();
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        if (!UserMgr.INSTANCE.playerLogin(userId, playerId)){
            user.sendTips(19);
            return;
        }
        RpcGateProxyHolder.getInstance(IRpcPlayerBase.class, user.getServerId())
                .playerLogin(RpcGateProxyHolder.gateTopic(), playerId);
    }

    /**
     * 测试消息
     *
     */
    public void message03(MessageProto.Message message, long userId) {
        var user = UserMgr.INSTANCE.getUserByUserId(userId);

        RpcGateProxyHolder.getInstance(IRpcPlayerBase.class, user.getServerId()).test("hello");
        var context = RpcGateProxyHolder.getInstance(IRpcPlayerBase.class, user.getServerId())
                .testString("hello");
        System.out.println(context);
    }

    /**
     * 获取角色列表
     *
     */
    public void message04(MessageProto.Message message, long userId) {
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        ReqPlayerListMessage playerListMessage = new ReqPlayerListMessage();
        playerListMessage.setUserId(userId);
        playerListMessage.setServerId(user.getServerId());
        List<PlayerEntityMessage> players = HttpClient.getInstance().getPlayerList(playerListMessage);

        List<Long> playerIds = players.stream().map(PlayerEntityMessage::getId).toList();

        var serverId = user.getServerId();
        var playerBaseList = RpcGateProxyHolder.getInstance(IRpcPlayerBase.class, serverId).getPlayers(new ListWrapper<>(playerIds));
        var builder = PlayerInfoProto.PlayerInfos.newBuilder();
        for (var playerEntity : playerBaseList.immutableList()) {
            var playerInfo = PlayerInfoProto.PlayerInfo.newBuilder().setId(playerEntity.getId())
                    .setCareer(playerEntity.getCareer()).setSex(playerEntity.getSex())
                    .setUserId(userId)
                    .setLastLogin(playerEntity.getLastLogin());
            builder.addPlayerInfos(playerInfo);
        }

        user.getServerInfo().setPlayerIds(new HashSet<>(playerIds));
        MessageProto.Message.Builder messageBuilder = MessageProto.Message.newBuilder();
        messageBuilder.setCommand(0x0004);
        messageBuilder.setPlayerInfos(builder);
        user.sendToClientMessage(messageBuilder);
    }

    /**
     * tips
     *
     */
    public void message05(MessageProto.Message message, long userId) {
    }
}
