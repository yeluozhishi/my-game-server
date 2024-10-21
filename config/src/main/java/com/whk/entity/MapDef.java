package com.whk.entity;

import com.whk.loadconfig.annotation.Column;
import com.whk.loadconfig.convert.PoundArrayConvertor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapDef {
    public int id;
    public String name;
    public int type;
    public int data;
    public int width;
    public int height;
    @Column(convertor = PoundArrayConvertor.PoundToInteger.class)
    public Integer[] reliveCoord;
    public int countKey;
    public int overlap;
    public int restoreHP;
    public int orInstanceRequest;

    public int groupId;
    public int line;
    public int virMap;
    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    public List<Integer> callType;

    public int flyScroll;

    //0默认为允许1为不允许（传送符+土灵符）;
    public int transmit;

    public int restoreEXP;

    public int killedMail;

    public int bindDay;

    public int noBack;

    public int disuse;

    /**
     * 1元宝复活 2江湖币复活 3免费复活
     */
    @Column(convertor = PoundArrayConvertor.PoundToIntegerList.class)
    public List<Integer> reliveType;

    /**
     * 是否安全地图
     * 1=安全 0或空=危险
     */
    public int safe;

    public int pk;
    public int teamLimit;

    public int chatid;
    public int addexperienceRate;

    public int bossReviveShow;
    public int addexperienceRate2;
    public int needtime;
}
