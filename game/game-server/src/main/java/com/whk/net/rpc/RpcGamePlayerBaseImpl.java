package com.whk.net.rpc;

import com.whk.actor.PlayerMgr;
import com.whk.net.rpc.annotation.RpcTag;
import com.whk.net.rpc.api.game.IRpcGamePlayerBase;
import com.whk.protobuf.message.CreatePlayerProto;
import com.whk.script.IPlayerScript;
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
    public void getPlayers(int gateServerId, long userId) {
        ScriptHolder.INSTANCE.getScript(IPlayerScript.class).getPlayers(gateServerId, userId);
    }

    @Override
    public void createPlayer(String gateTopic, int gateServerId, CreatePlayerProto.CreatePlayer message) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        PlayerMgr.INSTANCE.creatPlayer(gateTopic, gateServerId, message);
    }

    @Override
    public void playerLogin(String gateTopic, long playerId, int gateServerId, long userId) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        log.info("角色登录 playerId:{} , gateServerId:{}", playerId, gateServerId);
        PlayerMgr.INSTANCE.playerLogin(gateTopic, playerId, gateServerId, userId);
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
    public void noticeEnterSceneState(long serverId, long playerId) {
        ScriptHolder.INSTANCE.getScript(IPlayerScript.class).noticeEnterSceneState(serverId, playerId);
    }

    @Override
    public void pushDataToScene(long playerId, int mapId, int line, long serverId) {
        ScriptHolder.INSTANCE.getScript(IPlayerScript.class).pushDataToScene(playerId, mapId, line, serverId);
    }
}
