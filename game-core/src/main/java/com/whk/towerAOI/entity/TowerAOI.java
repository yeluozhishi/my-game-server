package com.whk.towerAOI.entity;

import com.whk.entity.MapDef;
import com.whk.towerAOI.script.ITowerScript;
import lombok.Getter;
import lombok.Setter;
import script.ScriptHolder;

/**
 * 单个地图的tower管理器
 */
@Getter
@Setter
public class TowerAOI {
    /**
     * 灯塔
     */
    private Tower[][] towers;

    private String sceneId;

    private int mapHeight;

    private int mapWidth;

    private int towerXSize;

    private int towerYSize;


    private int maxTowerX;

    private int maxTowerY;

    public TowerAOI(String sceneId, int towerXSize, int towerYSize, MapDef mapDef) {
        this.sceneId = sceneId;
        this.mapHeight = mapDef.getHeight();
        this.mapWidth = mapDef.getWidth();
        this.towerXSize = towerXSize;
        this.towerYSize = towerYSize;
    }

    public void init(Topography topography) {
        ScriptHolder.INSTANCE.getScript(ITowerScript.class).initTowerAOI(this, topography);
    }

}
