package com.whk.scene;

import com.whk.actor.Player;
import com.whk.skill.Skill;
import com.whk.skill.SkillProcessor;
import com.whk.threadpool.HandlerFactory;
import com.whk.threadpool.QueueDriver;
import com.whk.threadpool.ThreadType;
import com.whk.threadpool.ThreadPoolManager;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

@Setter
@Getter
public abstract class AbstractScene implements SceneInterface {

    private Map<Long, Player> playerMap = new HashMap<>();

    protected SkillProcessor skillProcessor = new SkillProcessor();

    protected QueueDriver driver;

    public AbstractScene(ThreadType threadType) {
        this.driver = new QueueDriver(ThreadPoolManager.getInstance().getExecutor(threadType), new LinkedBlockingQueue<>());
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
