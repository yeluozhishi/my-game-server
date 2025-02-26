package com.whk.net;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public enum Router {
    INSTANCE;

    private final Set<Integer> sceneCmd = new HashSet<>();

    public boolean sceneMessage(int cmd) {
        return sceneCmd.contains(cmd);
    }


    Router() {
        sceneCmd.add(102);
        sceneCmd.add(103);
    }
}
