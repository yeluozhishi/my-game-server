package com.whk.skill;

import com.whk.script.ISkillScript;
import script.ScriptHolder;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SkillProcessor {

    List<Skill> skills = new LinkedList<>();
    final List<Skill> addList = new LinkedList<>();

    public void skillDeal() {
        for (Skill skill : skills) {
            execute(skill);
            if (skill.isFinish() && Objects.nonNull(skill.getNextSkill())) {
                addList.add(skill.nextSkill);
            }
        }

        skills.clear();
        synchronized (addList) {
            skills.addAll(addList);
            addList.clear();
        }
    }

    public void addSkill(Skill skill) {
        if (skill.getSource().getStatuses().isDeath()) return;
        addList.add(skill);
    }

    public void execute(Skill skill) {
        if (skill.getSource().getStatuses().isDeath()) return;
        ScriptHolder.INSTANCE.getScript(ISkillScript.class).executeScript(skill);
    }

}
