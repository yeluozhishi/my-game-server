package com.whk.scene.map;

import cn.hutool.core.util.RandomUtil;
import com.whk.actor.PlayerActor;
import com.whk.entity.MapDef;
import com.whk.scene.SceneInterface;
import com.whk.scene.skill.Skill;
import com.whk.scene.skill.SkillProcessor;
import com.whk.threadpool.HandlerFactory;
import com.whk.threadpool.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import com.whk.towerAOI.entity.Point;
import com.whk.towerAOI.entity.Topography;
import com.whk.towerAOI.entity.Tower;
import com.whk.towerAOI.entity.TowerAOI;
import com.whk.towerAOI.script.ITowerScript;
import lombok.Getter;
import lombok.Setter;
import script.ScriptHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Setter
@Getter
public abstract class AbstractScene implements SceneInterface {

    private Map<Long, PlayerActor> playerMap = new HashMap<>();

    protected SkillProcessor skillProcessor = new SkillProcessor();

    protected QueueDriver driver;

    private String sceneId;

    private MapDef mapDef;

    private Topography topography;

    private TowerAOI towerAOI;

    public AbstractScene(MapDef mapDef) {
        this.driver = new QueueDriver(ThreadPoolManager.getInstance().getExecutor(ThreadType.SCENE_THREAD),
                "场景驱动器-%s".formatted(getSceneId()), new ConcurrentLinkedQueue<>());
        sceneId = "%d_%d".formatted(mapDef.getId(), mapDef.getLine());
        topography = new Topography(mapDef);
        towerAOI = new TowerAOI(sceneId, 100, 100, mapDef);
        this.mapDef = mapDef;
    }

    public void init() {
        topography.init(mapDef);
        towerAOI.init(topography);
    }

    public abstract void sceneTick();

    public void addEvent(String sceneId, Runnable runnable) {
        driver.addEvent(HandlerFactory.INSTANCE.creatSceneHandler(sceneId, runnable));
    }

    public void tick() {
        addEvent(this.getSceneId(), () -> skillProcessor.skillDeal());
        addEvent(this.getSceneId(), this::sceneTick);
    }

    public void addSkill(Skill skill) {
        skillProcessor.addSkill(skill);
    }


}
