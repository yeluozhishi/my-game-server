package com.whk.towerAOI.script;

import com.whk.towerAOI.entity.*;
import script.scriptInterface.IScript;

import java.util.List;
import java.util.Set;


public interface ITowerScript extends IScript {
    boolean addObject(TowerAOI towerAOI, IMapObject obj);

    boolean removeObject(TowerAOI towerAOI, IMapObject obj);

    void addWatcher(TowerAOI towerAOI, IMapObject obj);

    void removeWatcher(TowerAOI towerAOI, IMapObject obj);

    void initTowerAOI(TowerAOI towerAOI, Topography topography);


    Set<Tower> getTowerAndNearTower(TowerAOI towerAOI, IMapObject obj, Topography topography);
}
