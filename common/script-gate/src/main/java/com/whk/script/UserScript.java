package com.whk.script;

import cn.hutool.core.util.RandomUtil;
import com.whk.SpringUtils;
import com.whk.config.GatewayServerConfig;
import com.whk.message.MESSAGE_CODE;
import com.whk.message.MapBean;
import com.whk.message.Server;
import com.whk.message.gamegate.PlayerEntityMessage;
import com.whk.message.gamegate.ReqCreatePlayerMessage;
import com.whk.message.gamegate.ReqPlayerListMessage;
import com.whk.net.GateSendMessageHolder;
import com.whk.net.RpcGateProxyHolder;
import com.whk.net.http.HttpClient;
import com.whk.net.rpc.api.game.IRpcGamePlayerBase;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import com.whk.net.rpc.serialize.wrapper.ListWrapper;
import com.whk.protobuf.message.CreatePlayerProto;
import com.whk.protobuf.message.PlayerInfoProto;
import com.whk.protobuf.message.SceneProto;
import com.whk.server.GateServerManager;
import com.whk.serverinfo.ServerType;
import com.whk.user.User;
import com.whk.user.UserMgr;
import lombok.extern.slf4j.Slf4j;
import script.annotation.Script;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Script
@Slf4j
public class UserScript implements IUserScript {

    @Override
    public void noticeEnterSceneState(int serverId, long playerId) {
        var user = UserMgr.INSTANCE.getUserByPlayerId(playerId);
        Server server = GateServerManager.getInstance().getServer(serverId);
        if (Objects.nonNull(server)) {
            user.getServerInfo().setSceneServer(server);
            SceneProto.ResEnterScene.Builder builder = SceneProto.ResEnterScene.newBuilder();
            builder.setDesc("进入场景:" + server);
            GateSendMessageHolder.getInstance().sendToClientMessage(builder.build(), user.getUserId());
        } else {
            GateSendMessageHolder.getInstance().sendTips(MESSAGE_CODE.升级失败, user.getUserId());
        }
    }

    @Override
    public void createPlayer(CreatePlayerProto.CreatePlayer message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var serverId = message.getServerId();
        var sex = message.getSex();
        var kind = message.getKind();
        var name = message.getName();
        // 获取playerId
        ReqCreatePlayerMessage reqCreatePlayerMessage = new ReqCreatePlayerMessage();
        reqCreatePlayerMessage.setKind(kind);
        reqCreatePlayerMessage.setSex(sex);
        reqCreatePlayerMessage.setUserId(userId);
        var map = HttpClient.getInstance().createPlayer(reqCreatePlayerMessage, MapBean.class);

        long pid = map.getLong("pid");
        User user = UserMgr.INSTANCE.getUserByUserId(userId);
        if (pid != 0) {
            GatewayServerConfig serverConfig = SpringUtils.getBean(GatewayServerConfig.class);
            var result = RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, serverId)
                    .createPlayer(serverConfig.getTopic(), pid, serverConfig.getGameDateConfig().getServer(), name);

            if (result.getInt(MapBean.CODE_TAG) != MESSAGE_CODE.创建角色成功.getCode()) {
                GateSendMessageHolder.getInstance().sendTips(result, userId);
                return;
            }

            user.getServerInfo().getPlayerIds().add(pid);
            if (!UserMgr.INSTANCE.playerLogin(user, pid)) {
                GateSendMessageHolder.getInstance().sendTips(MESSAGE_CODE.角色登录失败, userId);
                return;
            }
        }
        GateSendMessageHolder.getInstance().sendTips(MESSAGE_CODE.创建角色成功, userId);
    }

    @Override
    public void playerLogin(PlayerInfoProto.ReqPlayerLogin message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playerId = message.getPlayerId();
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        if (!UserMgr.INSTANCE.playerLogin(user, playerId)) {
            GateSendMessageHolder.getInstance().sendTips(MESSAGE_CODE.角色登录失败, userId);
            return;
        }
        GatewayServerConfig serverConfig = SpringUtils.getBean(GatewayServerConfig.class);
        var result = RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerId())
                .playerLogin(serverConfig.getTopic(), playerId, serverConfig.getGameDateConfig().getServer());
        if (result.getInt(MapBean.CODE_TAG) != MESSAGE_CODE.角色登录成功.getCode()) {
            GateSendMessageHolder.getInstance().sendTips(result, userId);
        }
    }

    @Override
    public void getPlayerList(PlayerInfoProto.ReqPlayers message, long userId) {
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        ReqPlayerListMessage playerListMessage = new ReqPlayerListMessage();
        playerListMessage.setUserId(userId);
        playerListMessage.setServerId(user.getServerId());
        List<PlayerEntityMessage> players = HttpClient.getInstance().getPlayerList(playerListMessage);

        List<Long> playerIds = players.stream().map(PlayerEntityMessage::getId).toList();

        var playerBaseList = RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerId()).getPlayers(userId, new ListWrapper<>(playerIds));
        var builder = PlayerInfoProto.PlayerInfos.newBuilder();
        for (var playerEntity : playerBaseList.immutableList()) {
            var playerInfo = PlayerInfoProto.PlayerInfo.newBuilder().setId(playerEntity.getId())
                    .setCareer(playerEntity.getCareer()).setSex(playerEntity.getSex())
                    .setUserId(userId)
                    .setLastLogin(playerEntity.getLastLogin());
            builder.addPlayerInfos(playerInfo);
        }

        user.getServerInfo().setPlayerIds(new HashSet<>(playerIds));
        GateSendMessageHolder.getInstance().sendToClientMessage(builder.build(), userId);
    }

    @Override
    public void enterScene(SceneProto.ReqEnterScene message, long userId) {
        // 获取场景服务器
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        // 判断是否在场景服务器中
        if (user.getServerInfo().inScene()) {
            RpcGateProxyHolder.getInstance().proxy(IRpcScenePlayerActor.class, user.getServerInfo().getSceneServer().getId())
                    .enterScene(user.getServerInfo().getPlayerId(), message.getSceneId());
        } else {
            Integer serverId = RandomUtil.randomEle(GateServerManager.getInstance().getGroupServers(ServerType.SCENE).keySet().stream().toList());
            RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerInfo().getSceneServer().getId())
                    .pushDataToScene(user.getServerInfo().getPlayerId(), message.getSceneId(), serverId);
        }
    }

    @Override
    public void testMsg(PlayerInfoProto.TestMessage message, long userId) {
        log.info(message.getMsg());
        var user = UserMgr.INSTANCE.getUserByUserId(userId);

        RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerId()).test("hello");
        var context = RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerId())
                .testString("hello");
        log.info(context);
        GateSendMessageHolder.getInstance().sendTips(MESSAGE_CODE.已经接收消息, userId, context);
    }
}
