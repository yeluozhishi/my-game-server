package com.whk.script;

import cn.hutool.core.util.RandomUtil;
import com.whk.actor.PlayerActor;
import com.whk.config.MapConfig;
import com.whk.entity.MapDef;
import com.whk.net.rpc.api.game.IRpcGamePlayerBase;
import com.whk.net.rpc.api.gate.IRpcGateServerInfoService;
import com.whk.scene.actor.Movement;
import com.whk.scene.actor.PlayerActorMgr;
import com.whk.scene.map.AbstractScene;
import com.whk.scene.map.DefaultScene;
import com.whk.scene.map.SceneManager;
import com.whk.scene.map.script.ISceneScript;
import com.whk.scene.net.RpcSceneProxyHolder;
import com.whk.scene.server.SceneServerManager;
import com.whk.towerAOI.entity.Point;
import com.whk.towerAOI.entity.Tower;
import com.whk.towerAOI.entity.View;
import com.whk.towerAOI.script.ITowerScript;
import lombok.extern.slf4j.Slf4j;
import script.ScriptHolder;
import script.annotation.Script;

import java.util.Objects;

@Script
@Slf4j
public class SceneScript implements ISceneScript {
    @Override
    public void createMainScene() {
        MapConfig.getInstance().getHashMap().values().forEach(configDef -> {
            if (configDef.getType() == 1 && configDef.getLine() == 1) {
                AbstractScene scene = buildScene(configDef, SceneManager.INSTANCE.getMapPath());
                SceneManager.INSTANCE.addScene(scene);
            }
        });
    }

    public AbstractScene buildScene(MapDef mapDef, String mapPath) {
        DefaultScene scene = new DefaultScene(mapDef);
        scene.init(mapPath);
        return scene;
    }

    @Override
    public void pushDataAndEnterScene(PlayerActor actor, int mapId, int line) {
        PlayerActorMgr.INSTANCE.addPlayerActor(actor);
        log.info("玩家进入场景：{}, mapId：{}, line: {}", actor.getId(), mapId, line);
        AbstractScene scene = SceneManager.INSTANCE.getScene(mapId, line);
        if (Objects.isNull(scene)) return;
        playerEnterScene(scene, actor);

        RpcSceneProxyHolder.getInstance().proxy(IRpcGamePlayerBase.class, actor.getDateServerId())
                .noticeEnterSceneState(SceneServerManager.getInstance().getLocalHost().getId(), actor.getId());
        RpcSceneProxyHolder.getInstance().proxy(IRpcGateServerInfoService.class, actor.getGateServerId())
                .noticeEnterSceneState(SceneServerManager.getInstance().getLocalHost().getId(), actor.getId());
    }

    @Override
    public void playerEnterScene(long playerId, int mapId, int line) {
        PlayerActor actor = PlayerActorMgr.INSTANCE.getPlayer(playerId);
        if (Objects.isNull(actor)) return;
        log.info("玩家进入场景：{}, mapId：{}, line: {}", actor.getId(), mapId, line);
        AbstractScene scene = SceneManager.INSTANCE.getScene(mapId, line);
        if (Objects.isNull(scene) || scene.getSceneId() == actor.getMovement().getScene().getSceneId()) {
            return;
        }

        AbstractScene oldScene = (AbstractScene) actor.getMovement().getScene();
        playerLeaveScene(oldScene, actor.getId());
        playerEnterScene(scene, actor);
    }


    public void playerLeaveScene(AbstractScene oldScene, long playerId) {
        var player = oldScene.getPlayerMap().remove(playerId);
        ScriptHolder.INSTANCE.getScript(ITowerScript.class).removeWatcher(oldScene.getTowerAOI(), player);
    }

    public void playerEnterScene(AbstractScene scene, PlayerActor actor) {
        Point point = RandomUtil.randomEle(scene.getTopography().getBornPoint());
        Tower tower = ScriptHolder.INSTANCE.getScript(ITowerScript.class).getTower(scene.getTowerAOI(), point);
        ScriptHolder.INSTANCE.getScript(ITowerScript.class).addWatcher(tower, actor);

        Movement movement = new Movement();
        actor.setMovement(movement);
        movement.setScene(scene);
        movement.setPoint(point);

        View view = new View();
        view.setHeight(50);
        view.setWidth(50);
        actor.setView(view);

        scene.getPlayerMap().put(actor.getId(), actor);
    }
}
