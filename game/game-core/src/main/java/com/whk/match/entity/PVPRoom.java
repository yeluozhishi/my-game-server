package com.whk.match.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class PVPRoom extends Room {

    // 队伍
    private Map<Long, Team> TwoTeams = new HashMap<>();

}
