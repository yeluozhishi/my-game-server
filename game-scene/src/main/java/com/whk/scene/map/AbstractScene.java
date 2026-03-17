package com.whk.scene.map;

import com.whk.actor.PlayerActor;
import com.whk.entity.MapDef;
import com.whk.scene.SceneInterface;
import com.whk.scene.event.AbstractSceneEvent;
import com.whk.scene.event.SceneTickEvent;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.driver.QueueDriver;
import com.whk.towerAOI.entity.Topography;
import com.whk.towerAOI.entity.TowerAOI;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Setter
@Getter
public abstract class AbstractScene implements SceneInterface {

    private Map<Long, PlayerActor> playerMap = new HashMap<>();

    protected QueueDriver driver;

    private String sceneId;

    private MapDef mapDef;

    private Topography topography;

    private TowerAOI towerAOI;

    public AbstractScene(MapDef mapDef) {
        sceneId = "%d_%d".formatted(mapDef.getId(), mapDef.getLine());
        this.driver = new QueueDriver(ThreadPoolManager.getInstance().getExecutor(ThreadType.SCENE_THREAD),
                "场景驱动器-%s".formatted(getSceneId()), new ConcurrentLinkedQueue<>());
        topography = new Topography(mapDef);
        towerAOI = new TowerAOI(sceneId, 100, 100, mapDef);
        this.mapDef = mapDef;
    }

    public void init(String mapPath) {
        topography.init(mapDef, mapPath);
        towerAOI.init(topography);
    }

    public abstract void sceneTick();

    public void addEvent(AbstractSceneEvent event) {
        driver.addEvent(event);
    }

    public void tick() {
        addEvent(new SceneTickEvent(this));
    }

}
