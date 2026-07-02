package com.whk.script;

import cn.hutool.core.util.RandomUtil;
import com.whk.SpringUtils;
import com.whk.config.GatewayServerConfig;
import com.whk.message.MESSAGE_CODE;
import com.whk.message.MapBean;
import com.whk.message.Server;
import com.whk.message.gamegate.ReqCreatePlayerMessage;
import com.whk.message.gamegate.ReqPlayerListMessage;
import com.whk.net.MessageUtil;
import com.whk.net.RpcGateProxyHolder;
import com.whk.net.http.HttpClient;
import com.whk.net.rpc.api.game.IRpcGamePlayerBase;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import com.whk.net.rpc.model.PlayerInfo;
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
import java.util.List;
import java.util.Objects;

@Script
@Slf4j
public class UserScript implements IUserScript {

    @Override
    public void noticeEnterSceneState(long serverId, long playerId) {
        var user = UserMgr.INSTANCE.getUserByPlayerId(playerId);
        Server server = GateServerManager.getInstance().getServer(serverId);
        if (Objects.nonNull(server)) {
            user.getServerInfo().setSceneServer(server);
            SceneProto.ResEnterScene.Builder builder = SceneProto.ResEnterScene.newBuilder();
            builder.setDesc("进入场景:" + server);
            MessageUtil.getInstance().sendToClientMessage(builder.build(), user.getUserId());
        } else {
            MessageUtil.getInstance().sendTips(MESSAGE_CODE.升级失败, user.getUserId());
        }
    }

    @Override
    public void createPlayer(CreatePlayerProto.CreatePlayer message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        GatewayServerConfig serverConfig = SpringUtils.getBean(GatewayServerConfig.class);
        RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, message.getServerId())
                .createPlayer(serverConfig.getTopic(), serverConfig.getGameDateConfig().getServer(), message);
    }

    @Override
    public void resCreatePlayerFailure(MapBean messageMapBean, long userId) {
        MessageUtil.getInstance().sendTips(messageMapBean, userId);
    }

    @Override
    public void resCreatePlayer(MapBean mapBean, long userId) {
        long playerId = mapBean.getLong("playerId");
        int career = mapBean.getInt("career");
        int sex = mapBean.getInt("sex");
        long serverId = mapBean.getLong("serverId");
        User user = UserMgr.INSTANCE.getUserByUserId(userId);

        user.getServerInfo().getPlayerIds().add(playerId);
        // 获取playerId
        ReqCreatePlayerMessage reqCreatePlayerMessage = new ReqCreatePlayerMessage();
        reqCreatePlayerMessage.setKind(career);
        reqCreatePlayerMessage.setSex(sex);
        reqCreatePlayerMessage.setUserId(userId);
        reqCreatePlayerMessage.setPlayerId(playerId);
        reqCreatePlayerMessage.setServerId(serverId);
        HttpClient.getInstance().createPlayer(reqCreatePlayerMessage);
        if (UserMgr.INSTANCE.playerLogin(user, playerId)) {
            MessageUtil.getInstance().sendTips(MESSAGE_CODE.角色登录失败, userId);
            return;
        }
        MessageUtil.getInstance().sendTips(MESSAGE_CODE.创建角色成功, userId);
    }

    @Override
    public void playerLogin(PlayerInfoProto.ReqPlayerLogin message, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var playerId = message.getPlayerId();
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        if (UserMgr.INSTANCE.playerLogin(user, playerId)) {
            MessageUtil.getInstance().sendTips(MESSAGE_CODE.角色登录失败, userId);
            return;
        }
        GatewayServerConfig serverConfig = SpringUtils.getBean(GatewayServerConfig.class);
        RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerId())
                .playerLogin(serverConfig.getTopic(), playerId, serverConfig.getGameDateConfig().getServer(), userId);
    }

    @Override
    public void resPlayerLogin(long userId, MapBean messageMapBean) {
        MessageUtil.getInstance().sendTips(messageMapBean, userId);
    }

    @Override
    public void getPlayerList(PlayerInfoProto.ReqPlayers message, long userId) {
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        ReqPlayerListMessage playerListMessage = new ReqPlayerListMessage();
        playerListMessage.setUserId(userId);
        playerListMessage.setServerId(user.getServerId());
        GatewayServerConfig serverConfig = SpringUtils.getBean(GatewayServerConfig.class);
        RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerId())
                .getPlayers(serverConfig.getGameDateConfig().getServer(), userId);

    }

    @Override
    public void resGetPlayers(List<PlayerInfo> result, long userId) {
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        var builder = PlayerInfoProto.PlayerInfos.newBuilder();

        for (var playerEntity : result) {
            var playerInfo = PlayerInfoProto.PlayerInfo.newBuilder().setId(playerEntity.getId())
                    .setCareer(playerEntity.getCareer()).setSex(playerEntity.getSex())
                    .setUserId(userId).setName(playerEntity.getName())
                    .setLastLogin(playerEntity.getLastLogin());
            builder.addPlayerInfos(playerInfo);
            user.getServerInfo().getPlayerIds().add(playerEntity.getId());
        }

        MessageUtil.getInstance().sendToClientMessage(builder.build(), userId);
    }

    @Override
    public void enterScene(SceneProto.ReqEnterScene message, long userId) {
        // 获取场景服务器
        var user = UserMgr.INSTANCE.getUserByUserId(userId);
        // 判断是否在场景服务器中
        if (user.getServerInfo().inScene()) {
            RpcGateProxyHolder.getInstance().proxy(IRpcScenePlayerActor.class, user.getServerInfo().getSceneServer().getId())
                    .enterScene(user.getServerInfo().getPlayerId(), message.getMapId(), message.getLine());
        } else {
            long serverId = RandomUtil.randomEle(GateServerManager.getInstance().getGroupServers(ServerType.SCENE).keySet().stream().toList());
            RpcGateProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, user.getServerInfo().getSceneServer().getId())
                    .pushDataToScene(user.getServerInfo().getPlayerId(), message.getMapId(), message.getLine(), serverId);
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
        MessageUtil.getInstance().sendTips(MESSAGE_CODE.已经接收消息, userId, context);
    }


}
