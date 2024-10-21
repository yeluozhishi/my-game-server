package com.whk.script;

import com.whk.towerAOI.entity.*;
import com.whk.towerAOI.script.ITowerScript;
import script.annotation.Script;

import java.util.*;
import java.util.logging.Logger;

@Script
public class TowerScript implements ITowerScript {

    private final Logger logger = Logger.getLogger(TowerScript.class.getName());

    @Override
    public boolean addObject(TowerAOI towerAOI, IMapObject obj) {
        if (Objects.isNull(obj)) return false;
        Tower tower = getTower(towerAOI, obj.getPoint());
        if (Objects.isNull(tower)) return false;
        tower.getObjectMap().put(obj.getId(), obj);
        return true;
    }

    @Override
    public boolean removeObject(TowerAOI towerAOI, IMapObject obj) {
        if (Objects.isNull(obj)) return false;
        Tower tower = getTower(towerAOI, obj.getPoint());
        if (Objects.isNull(tower)) return false;
        tower.getObjectMap().remove(obj.getId(), obj);
        return false;
    }

    @Override
    public void addWatcher(TowerAOI towerAOI, IMapObject obj) {
        if (Objects.isNull(obj)) return;
        Tower tower = getTower(towerAOI, obj.getPoint());
        tower.getObjectMap().put(obj.getId(), obj);
        tower.getWatchers().put(obj.getId(), obj);
    }

    @Override
    public void removeWatcher(TowerAOI towerAOI, IMapObject obj) {
        if (Objects.isNull(obj)) return;
        Tower tower = getTower(towerAOI, obj.getPoint());
        tower.getObjectMap().remove(obj.getId(), obj);
        tower.getWatchers().remove(obj.getId());
    }

    @Override
    public void initTowerAOI(TowerAOI towerAOI, Topography topography) {
        int maxTowerX = Math.floorDiv(towerAOI.getMapWidth(), towerAOI.getTowerSize());
        int maxTowerY = Math.floorDiv(towerAOI.getMapHeight(), towerAOI.getTowerSize());

        towerAOI.setMaxTowerX(maxTowerX);
        towerAOI.setMaxTowerY(maxTowerY);

        Tower[][] towers = new Tower[maxTowerX][maxTowerY];
        for (int x = 0; x < maxTowerX; x++) {
            for (int y = 0; y < maxTowerY; y++) {
                Tower tower = new Tower();
                tower.setX(x);
                tower.setY(y);
                towers[x][y] = tower;
            }
        }

        for (Point[] points : topography.getAllPoint()) {
            for (Point point : points) {
                getTower(towerAOI, point);
            }
        }

        towerAOI.setTowers(towers);
    }

    @Override
    public Set<Tower> getTowerAndNearTower(TowerAOI towerAOI, IMapObject obj, Topography topography) {
        Set<Tower> towers = new HashSet<>();
        Point point = obj.getPoint();
        towers.add(getTower(towerAOI, point));

        // 周围tower
        View view = obj.getView();
        int hx = view.getHeight() / 2;
        int wy = view.getWidth() / 2;
        // 视野矩形区域
        for (int x = point.getX() - hx; x <= point.getX() + hx; x++) {
            for (int y = point.getY() - wy; y <= point.getY() + wy; y++) {
                if (x < 0 || y < 0 || x >= topography.getAllPoint().length || y >= topography.getAllPoint()[0].length)
                    continue;
                towers.add(getTower(towerAOI, topography.getAllPoint()[x][y]));
            }
        }
        towers.remove(null);
        return towers;
    }

    public Tower getTower(TowerAOI towerAOI, Point point) {
        if (Objects.isNull(point)) return null;
        if (Objects.nonNull(point.getTower())) return point.getTower();
        int towerX = Math.floorDiv(point.getX(), towerAOI.getTowerSize());
        int towerY = Math.floorDiv(point.getY(), towerAOI.getTowerSize());
        if (towerX >= towerAOI.getMaxTowerX() || towerY >= towerAOI.getMaxTowerY()) {
            logger.severe("获取灯塔失败，地图id：%d，x=%d，y=%d".formatted(towerAOI.getMapId(), point.getX(), point.getY()));
            return null;
        }
        point.setTower(towerAOI.getTowers()[towerX][towerY]);
        return point.getTower();
    }

}
