package com.whk.message;

import cn.hutool.core.util.RandomUtil;
import com.whk.SpringUtils;
import com.whk.annotation.GameMessageHandler;
import com.whk.annotation.HandlerDescription;
import com.whk.config.GatewayServerConfig;
import com.whk.message.gamegate.PlayerEntityMessage;
import com.whk.message.gamegate.ReqCreatePlayerMessage;
import com.whk.message.gamegate.ReqPlayerListMessage;
import com.whk.net.RpcGateProxyHolder;
import com.whk.net.http.HttpClient;
import com.whk.net.rpc.api.game.IRpcGamePlayerBase;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import com.whk.net.rpc.serialize.wrapper.ListWrapper;
import com.whk.protobuf.message.CreatePlayerProto;
import com.whk.protobuf.message.LoginProto;
import com.whk.protobuf.message.PlayerInfoProto;
import com.whk.protobuf.message.SceneProto;
import com.whk.server.GateServerManager;
import com.whk.serverinfo.ServerType;
import com.whk.threadpool.processor.ProcessorId;
import com.whk.user.User;
import com.whk.user.UserMgr;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;

@GameMessageHandler
@Slf4j
public class Handler00 {

    @HandlerDescription(number = 0, desc = "用户登录", processorId = ProcessorId.LOGIN_PROCESSOR)
    public void message00(LoginProto.LoginReq message, long userId) {
        System.out.printf("userId  %d  已登录。%n", userId);
    }

    @HandlerDescription(number = 1, desc = "角色创建", processorId = ProcessorId.PLAYER_PROCESSOR)
    public void message01(CreatePlayerProto.CreatePlayer message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
                user.sendTips(result);
                return;
            }

            user.getServerInfo().getPlayerIds().add(pid);
            if (!UserMgr.INSTANCE.playerLogin(user, pid)) {
                user.sendTips(MESSAGE_CODE.角色登录失败);
                return;
            }
        }
        user.sendTips(MESSAGE_CODE.创建角色成功);
    }

    @HandlerDescription(number = 2, desc = "角色登录")
    public void message02(PlayerInfoProto.ReqPlayerLogin message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playerId = message.getPlayerId();
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        if (!UserMgr.INSTANCE.playerLogin(user, playerId)) {
            user.sendTips(MESSAGE_CODE.角色登录失败);
            return;
        }
        GatewayServerConfig serverConfig = SpringUtils.getBean(GatewayServerConfig.class);
        var result = RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerId())
                .playerLogin(serverConfig.getTopic(), playerId, serverConfig.getGameDateConfig().getServer());
        if (result.getInt(MapBean.CODE_TAG) != MESSAGE_CODE.角色登录成功.getCode()) {
            user.sendTips(result);
        }
    }


    @HandlerDescription(number = 3, desc = "测试消息")
    public void message03(PlayerInfoProto.TestMessage message, long userId) {
        log.info(message.getMsg());
        var user = UserMgr.INSTANCE.getUserByUserId(userId);

        RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerId()).test("hello");
        var context = RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerId())
                .testString("hello");
        log.info(context);
        user.sendTips(MESSAGE_CODE.已经接收消息, context);
    }


    @HandlerDescription(number = 4, desc = "获取角色列表")
    public void message04(PlayerInfoProto.ReqPlayers message, long userId) {
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
        user.sendToClientMessage(PlayerInfoProto.PlayerInfos.class, builder.build().toByteString());
    }

    @HandlerDescription(number = 5, desc = "进入场景")
    public void message05(SceneProto.ReqEnterScene message, long userId) {
        // 进入场景
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
}
