package com.whk.script;

import cn.hutool.core.bean.BeanUtil;
import com.whk.SpringUtils;
import com.whk.actor.PlayerMgr;
import com.whk.actor.component.UserPlayer;
import com.whk.db.entity.PlayerEntity;
import com.whk.db.entity.UserPlayerEntity;
import com.whk.net.RpcGameProxyHolder;
import com.whk.net.rpc.api.gate.IRpcGateServerInfoService;
import com.whk.net.rpc.api.scene.IRpcScenePlayerActor;
import com.whk.net.rpc.model.PlayerInfo;
import com.whk.service.player.PlayerService;
import com.whk.service.user.UserPlayerService;
import org.springframework.data.domain.Example;
import script.annotation.Script;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Script
public class PlayerScript implements IPlayerScript {
    @Override
    public void pushDataToScene(long playerId, int mapId, int line, long serverId) {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(player)) return;
        var actor = PlayerMgr.INSTANCE.buildPlayerActor(player);
        RpcGameProxyHolder.getInstance().proxy(IRpcScenePlayerActor.class, serverId).pushDataAndEnterScene(actor, mapId, line);
    }

    @Override
    public void noticeEnterSceneState(long serverId, long playerId) {
        var player = PlayerMgr.INSTANCE.getPlayer(playerId);
        if (Objects.nonNull(player)) player.getServerInfo().setPresentServerId(serverId);
    }

    @Override
    public void getPlayers(int gateServerId, long userId) {
        var userPlayerService = SpringUtils.getBean(UserPlayerService.class);
        UserPlayerEntity userPlayerEntity = new UserPlayerEntity();
        userPlayerEntity.setUserId(userId);
        var userPlayers = userPlayerService.findByExample(userId, Example.of(userPlayerEntity));
        if (userPlayers.isEmpty()) {
            RpcGameProxyHolder.getInstance().proxy(IRpcGateServerInfoService.class, gateServerId).resGetPlayers(new ArrayList<>(), userId);
            return;
        }
        var service = SpringUtils.getBean(PlayerService.class);
        List<Long> playerIds = userPlayers.stream().map(f -> f.getId().playerId()).collect(Collectors.toList());
        var l = service.findAllByIds(userId, playerIds);
        List<PlayerInfo> result = l.stream().map(info -> {
            PlayerInfo playerInfo = new PlayerInfo();
            BeanUtil.copyProperties(info.getEntity(), playerInfo);
            return playerInfo;
        }).collect(Collectors.toList());
        RpcGameProxyHolder.getInstance().proxy(IRpcGateServerInfoService.class, gateServerId).resGetPlayers(result, userId);
    }
}
