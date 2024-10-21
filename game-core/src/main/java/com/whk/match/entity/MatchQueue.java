package com.whk.match.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MatchQueue {

    private Map<Long, Room> waitingRoom = new HashMap<>();


    private Map<Long, Room> readyRoom = new HashMap<>();


}
