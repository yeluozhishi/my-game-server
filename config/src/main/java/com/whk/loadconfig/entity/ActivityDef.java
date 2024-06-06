package com.whk.loadconfig.entity;

/**
 * @author Administrator
 */
public class ActivityDef {
    public int id;
    public int type;
    public int order;
    public String name;
    // 这个活动能参加次数,数组下标是目标序号, 如果没有默认为0
    public int[] activity_count;
    // 获取活跃度要求的次数
    public int degree_num;
    // 活动活跃度
    public int min_lev;
    public int degree;
    // 需要开服天数
    public int open_day;
    // 关闭活动天数设置 0为不关闭
    public int close_day;
    // 每周几可以玩
    public int[] activeDates;
//    // 活动时间
//    public activeTime: Array[TimeSpace] = _
//
//    //报名时间
//    public signUpTime: Array[TimeSpace] = _
//
//    //报名奖励
//    public signUpReward = List.empty[GiveItem]
    // 开服活动延续时间，0-不是开服活动
    public int open_active_day;
    // 开服活动期间，活动开启天数
    public int[] activeOrders;
    
}
