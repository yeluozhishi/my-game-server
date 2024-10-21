package com.whk.towerAOI.entity;

import com.whk.towerAOI.TOWER_DEFAULT;
import com.whk.towerAOI.script.ITowerScript;
import lombok.Getter;
import lombok.Setter;
import script.ScriptHolder;

import java.util.Objects;

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

    private int mapId;

    private int mapHeight;

    private int mapWidth;

    private int towerSize;


    private int maxTowerX;

    private int maxTowerY;

    public TowerAOI(int mapId, Topography topography) {
        this.mapId = mapId;
        this.mapHeight = topography.getHeight();
        this.mapWidth = topography.getWidth();
        this.towerSize = TOWER_DEFAULT.TOWER_SIZE;
        ScriptHolder.INSTANCE.getScript(ITowerScript.class).initTowerAOI(this, topography);
    }

}
