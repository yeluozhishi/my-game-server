package com.whk.scene.map;

import com.whk.actor.PlayerActor;
import com.whk.scene.SceneInterface;
import com.whk.scene.skill.Skill;
import com.whk.scene.skill.SkillProcessor;
import com.whk.threadpool.HandlerFactory;
import com.whk.threadpool.QueueDriver;
import com.whk.threadpool.ThreadPoolManager;
import com.whk.threadpool.ThreadType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Setter
@Getter
public abstract class AbstractScene implements SceneInterface {

    private Map<Long, PlayerActor> playerMap = new HashMap<>();

    protected SkillProcessor skillProcessor = new SkillProcessor();

    protected QueueDriver driver;

    public AbstractScene() {
        this.driver = new QueueDriver(ThreadPoolManager.getInstance().getExecutor(ThreadType.SCENE_THREAD),
                "场景驱动器-%d".formatted(getSceneId()), new ConcurrentLinkedQueue<>());
    }

    public abstract void sceneTick();

    public void tick() {
        driver.addEvent(HandlerFactory.INSTANCE.creatSceneHandler(() -> skillProcessor.skillDeal()));
        driver.addEvent(HandlerFactory.INSTANCE.creatSceneHandler(this::sceneTick));
    }

    public void addSkill(Skill skill) {
        skillProcessor.addSkill(skill);
    }
}
