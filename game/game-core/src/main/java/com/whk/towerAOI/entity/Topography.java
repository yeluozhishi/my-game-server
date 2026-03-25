package com.whk.towerAOI.entity;

import com.whk.entity.MapDef;
import com.whk.towerAOI.script.ITopographyScript;
import lombok.Getter;
import lombok.Setter;
import script.ScriptHolder;

import java.util.LinkedList;
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
    private List<Point> bornPoint = new LinkedList<>();
    /**
     * 可行走点
     */
    private List<Point> walkPoint = new LinkedList<>();

    public Topography(MapDef mapDef) {
        width = mapDef.getWidth();
        height = mapDef.getHeight();
    }

    public void init(MapDef mapDef, String mapPath) {
        ScriptHolder.INSTANCE.getScript(ITopographyScript.class).initTopography(this, mapDef, mapPath);
    }

    public Point getPoint(int x, int y){
        if (x < 0 || y < 0 || x >= getAllPoint().length || y > getAllPoint()[0].length) return null;
        return getAllPoint()[x][y];
    }
}
