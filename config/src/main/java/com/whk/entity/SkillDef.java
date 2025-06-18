package com.whk.entity;

import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.convert.PoundArrayConvertor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SkillDef {

    public int id;
    public String name;
    public String script;

    public int damageType;
    public int exp;

    /**
     * 消耗魔法
     */
    public int costMP;

    public int targetType;
    public int effectAreaType;
    public int speed;
    public int cdTime;
    public int stiffTime;
    public int hatePoint;

    public int maxTarget;

    public int releaseDistance;

    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    public Integer[] buff;

    public int effectAreaRange;

    public int subSkill;

    public int skillType;
    public int needLevel;

    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    public List<Integer> career;

    /**
     * 伤害飘字段数
     */
    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    public List<Integer> damagenum;

}