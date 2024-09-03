package com.whk.skill;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class SkillProcessor {

    List<Skill> skills = new LinkedList<>();
    List<Skill> temp = new CopyOnWriteArrayList<>();

    public void skillDeal() {
        List<Skill> newSkill = new LinkedList<>();
        for (Skill skill : skills) {
            skill.getStage().execute(skill);
            if (skill.getStage().isFinish()) {
                if (Objects.nonNull(skill.getStage().getNext())) {
                    skill.setStage(skill.getStage().getNext());
                } else {
                    continue;
                }
            }
            newSkill.add(skill);
        }

        skills.clear();
        skills.addAll(newSkill);
        skills.addAll(temp);
        temp.clear();
    }

    public void addSkill(Skill skill) {
        temp.add(skill);
    }

}
