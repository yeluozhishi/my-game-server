package com.whk;

import java.util.HashMap;
import java.util.Map;

public enum NavMeshManager {
    INSTANCE;

    Map<Integer, NavMeshService> meshServiceMap = new HashMap<>();

    public void addNavMesh(int id, NavMeshService navMeshService) {
        meshServiceMap.put(id, navMeshService);
    }

    public NavMeshService find(int id) {
        return meshServiceMap.get(id);
    }
}
