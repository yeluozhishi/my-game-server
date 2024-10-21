package com.whk.match.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Room {
    // id
    private long id;
    // 队伍
    private Map<Long, Team> OneTeams = new HashMap<>();
    // 评分
    private long score = 0;

    private boolean abandon = false;
}
