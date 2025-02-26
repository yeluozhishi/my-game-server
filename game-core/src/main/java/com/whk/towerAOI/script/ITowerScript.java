package com.whk.towerAOI.script;

import com.whk.towerAOI.entity.*;
import script.scriptInterface.IScript;

import java.util.Set;


public interface ITowerScript extends IScript {
    boolean addObject(TowerAOI towerAOI, IMapObject obj);

    boolean removeObject(TowerAOI towerAOI, IMapObject obj);

    void addWatcher(Tower towerAOI, IMapObject obj);

    void removeWatcher(Tower towerAOI, IMapObject obj);

    void initTowerAOI(TowerAOI towerAOI, Topography topography);


    Set<Tower> getNearTower(TowerAOI towerAOI, Point point, Topography topography, int halfWidth, int halfHeight);

    void moveToNextPoint(TowerAOI towerAOI, IMapObject obj, Topography topography, Point nextPoint);

    Tower getTower(TowerAOI towerAOI, Point point);
}
