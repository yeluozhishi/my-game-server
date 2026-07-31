package com.whk.towerAOI.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Point {
    private float x;

    private float y;

    private float z;

    private float dir;

    /**
     * 是否正常点
     */
    private boolean normal;
    /**
     * 是否是可走点 可走点是false
     */
    private boolean block;
    /**
     * 是否安全点
     */
    private boolean safe;
    /**
     * 可变化安全点
     */
    private boolean modifySafe;
    /**
     * 是否出生点
     */
    private boolean born;

    private boolean transmit;
    /**
     * 周围点
     */
    private Point[] nears;

    private Tower tower;

    public Point(float x, float y, float z, float dir) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dir = dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return Float.compare(x, point.x) == 0
            && Float.compare(y, point.y) == 0
            && Float.compare(z, point.z) == 0
            && Float.compare(dir, point.dir) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * (31 * (31 * Float.hashCode(x) + Float.hashCode(y)) + Float.hashCode(z)) + Float.hashCode(dir);
    }
}
