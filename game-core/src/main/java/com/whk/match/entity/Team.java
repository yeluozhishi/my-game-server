package com.whk.match.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Team {
    private long id;

    private Map<Long, Player> players = new HashMap<>();

    private long roomId;

    public boolean notice;
}
