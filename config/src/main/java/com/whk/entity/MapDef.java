package com.whk.entity;

import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.convert.PoundArrayConvertor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapDef {
    private int id;
    private String name;
    private int type;
    private int data;
    private int width;
    private int height;
    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    private int[] reliveCoord;
    private int countKey;
    private int overlap;
    private int restoreHP;
    private int orInstanceRequest;

    private int groupId;
    private int line;
    private int virMap;
    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    private List<Integer> callType;

    private int flyScroll;

    //0默认为允许1为不允许（传送符+土灵符）;
    private int transmit;

    private int restoreEXP;

    private int killedMail;

    private int bindDay;

    private int noBack;

    private int disuse;

    private int pvpKillCount;
    /**
     * 1元宝复活 2江湖币复活 3免费复活
     */
    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    private List<Integer> reliveType;

    /**
     * 是否安全地图
     * 1=安全 0或空=危险
     */
    private int safe;

    private int pk;
    private int teamLimit;

    private int chatid;
    private int addexperienceRate;

    private int bossReviveShow;
    private int addexperienceRate2;
    private int needtime;
}
