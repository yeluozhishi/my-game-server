package com.whk.loadconfig.entity;

import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.convert.MapConvertor;
import com.whk.loadconfig.convert.PoundArrayConvertor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@lombok.Getter
@lombok.Setter
public class SkillDef {

    public int id;
    public String name;
    public int level;
    public int type;
    public int groupId;

    public int damageType;
    public int exp;
    public int chargingTimes;

    @Column(convertor = MapConvertor.IntegerMap.class)
    public HashMap<Integer, Integer> needItem;

    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    public Integer[] needSkill;
    /**
     * 消耗魔法
     */
    public int costMP;
    public int costMPPercentage;
    public int costAG;
    public int needWeapon;
    public int needRide;

    public int targetType;
    public int effectArea;
    public int speed;
    public int runeEffect;
    public int cdTime;
    public int cdTimeGroup;
    public int publicCD;
    public int stiffTime;
    public int minDamage;
    public int maxDamage;
    public int hatePoint;

    public int maxTarget;
    public int hitsNumber;
    public int summonId;

    public int releaseDistance;

    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    public Integer[] buff;//给目标加buff;

    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    public Integer[] mapBuff;

    public String path;

    public int subSkill;

    public int exitRider;
    public int buildBuff;
    public int onHookPriority;

    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    public Integer[] pos;//客户端用了;
    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    public Integer[] move;

    public int probability;


    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    public Integer[] beforeBuff;//给自己加buff;
    /**
     * 学习这个技能的时候就添加这个buff
     */
    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    public Integer[] addbuff;

    public int skillType;
    public int comboPoint;
    public int skillBaseDmg;
    public int startPointType;
    public int castTargetType;
    /**
     * 武功威力
     */
    public int kungFu;
    public int kungFu_mon;
    public int needLevel;

    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    public List<Integer> career;

    /**
     * 伤害飘字段数
     */
    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    public List<Integer> damagenum;
    public int param;
    public int replaceskill;
    public int moveTarget;

    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    public List<Integer> extrabuff = new ArrayList<>();//正常升到这一级的时候就加这个buff,首饰套加成的技能不加;
}