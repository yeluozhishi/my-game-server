package com.whk.serverinfo;

public enum ServerType {
    GATE(1),
    GAME(2),
    SCENE(3);

    private final int type;

    ServerType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
