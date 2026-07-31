package com.whk.scene;

import com.whk.entity.MapDef;
import com.whk.towerAOI.entity.Topography;
import com.whk.towerAOI.entity.TowerAOI;

public interface SceneInterface {

    long getSceneId();

    TowerAOI getTowerAOI();

    Topography getTopography();

    MapDef getMapDef();
}
