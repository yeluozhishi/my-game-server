package com.whk.script;

import cn.hutool.core.util.RandomUtil;
import com.whk.ConfigCacheManager;
import com.whk.actor.PlayerActor;
import com.whk.comfig.MapConfig;
import com.whk.net.rpc.api.game.IRpcGamePlayerBase;
import com.whk.net.rpc.api.gate.IRpcGateServerInfoService;
import com.whk.scene.actor.Movement;
import com.whk.scene.actor.PlayerActorMgr;
import com.whk.scene.map.AbstractScene;
import com.whk.scene.map.SceneBuilder;
import com.whk.scene.map.SceneManager;
import com.whk.scene.map.script.ISceneScript;
import com.whk.scene.net.RpcSceneProxyHolder;
import com.whk.scene.server.SceneServerManager;
import com.whk.serverinfo.ServerManager;
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
        MapConfig config = ConfigCacheManager.INSTANCE.getConfigCache(MapConfig.class);
        config.getHashMap().values().forEach(configDef -> {
            if (configDef.getType() == 1 && configDef.getLine() == 1) {
                AbstractScene scene = SceneBuilder.build(configDef);
                SceneManager.INSTANCE.addScene(scene);
            }
        });
    }

    @Override
    public void playerEnterScene(PlayerActor actor, String sceneId) {
        PlayerActorMgr.INSTANCE.addPlayerActor(actor);
        log.info("玩家进入场景：{}, sceneId：{}", actor.getId(), sceneId);
        AbstractScene scene = SceneManager.INSTANCE.getScene(sceneId);
        scene.getPlayerMap().put(actor.getId(), actor);

        Point point = RandomUtil.randomEle(scene.getTopography().getBornPoint());
        Tower tower = ScriptHolder.INSTANCE.getScript(ITowerScript.class).getTower(scene.getTowerAOI(), point);
        ScriptHolder.INSTANCE.getScript(ITowerScript.class).addWatcher(tower, actor);

        Movement movement = (Movement) actor.getMovement();
        if (Objects.isNull(movement)) {
            movement = new Movement();
            actor.setMovement(movement);
        }
        movement.setScene(scene);
        movement.setPoint(point);

        View view = actor.getView();
        if (Objects.isNull(view)) {
            view = new View();
            view.setHeight(50);
            view.setWidth(50);
            actor.setView(view);
        }

        RpcSceneProxyHolder.getInstance(IRpcGamePlayerBase.class, actor.getDateServerId())
                .noticeEnterSceneState(SceneServerManager.getInstance().getLocalHost().getId(), actor.getId());
        RpcSceneProxyHolder.getInstance(IRpcGateServerInfoService.class, actor.getGateServerId())
                .noticeEnterSceneState(SceneServerManager.getInstance().getLocalHost().getId(), actor.getId());
    }
}
