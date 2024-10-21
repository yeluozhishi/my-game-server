package com.whk.towerAOI.entity;

import com.whk.entity.MapDef;
import com.whk.towerAOI.script.ITopographyScript;
import lombok.Getter;
import lombok.Setter;
import script.ScriptHolder;

import java.util.List;

@Getter
@Setter
public class Topography {
    private int height;

    private int width;

    /**
     * 所有的点(Array)
     */
    private Point[][] allPoint;
    /**
     * 出生点
     */
    private List<Point> bornPoint;
    /**
     * 可行走点
     */
    private List<Point> walkPoint;

    public Topography(MapDef mapDef) {
        ScriptHolder.INSTANCE.getScript(ITopographyScript.class).initTopography(this, mapDef);
    }

    public Point getPoint(int x, int y){
        if (x < 0 || y < 0 || x >= getAllPoint().length || y > getAllPoint()[0].length) return null;
        return getAllPoint()[x][y];
    }
}
