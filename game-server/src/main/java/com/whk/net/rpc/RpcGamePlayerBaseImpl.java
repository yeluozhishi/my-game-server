package com.whk.net.rpc;

import cn.hutool.core.bean.BeanUtil;
import com.whk.SpringUtils;
import com.whk.actor.PlayerMgr;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.game.IRpcGamePlayerBase;
import com.whk.net.rpc.model.PlayerInfo;
import com.whk.net.rpc.serialize.wrapper.ListWrapper;
import com.whk.script.IPlayerScript;
import com.whk.service.player.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import script.ScriptHolder;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Administrator
 */
@Component
@RpcTag
@Slf4j
public class RpcGamePlayerBaseImpl implements IRpcGamePlayerBase {


    @Override
    public ListWrapper<PlayerInfo> getPlayers(long userId, ListWrapper<Long> playerIds) {
        var service = SpringUtils.getBean(PlayerService.class);
        var l = service.findAllByIds(userId, playerIds.immutableList());
        return new ListWrapper<>(BeanUtil.copyToList(l, PlayerInfo.class));
    }

    @Override
    public void createPlayer(String gateTopic, Long pid, int gateServerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PlayerMgr.INSTANCE.creatPlayer(gateTopic, pid, gateServerId);
    }

    @Override
    public void playerLogin(String gateTopic, long playerId, int gateServerId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        log.info("角色登录完成");
        PlayerMgr.INSTANCE.playerLogin(gateTopic, playerId, gateServerId);
    }

    @Override
    public void test(String userName) {
        log.info(userName);
    }

    @Override
    public String testString(String context) {
        return "%s repeat".formatted(context);
    }

    @Override
    public void noticeEnterSceneState(int serverId, long playerId) {
        ScriptHolder.INSTANCE.getScript(IPlayerScript.class).noticeEnterSceneState(serverId, playerId);
    }

    @Override
    public void pushDataToScene(long playerId, String sceneId, Integer serverId) {
        ScriptHolder.INSTANCE.getScript(IPlayerScript.class).pushDataToScene(playerId, sceneId, serverId);
    }
}
