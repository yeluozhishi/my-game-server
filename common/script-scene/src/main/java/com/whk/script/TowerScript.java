package com.whk.script;

import com.whk.actor.PlayerActor;
import com.whk.protobuf.message.PlayerInfoProto;
import com.whk.protobuf.message.SceneProto;
import com.whk.scene.SceneInterface;
import com.whk.scene.net.MessageUtil;
import com.whk.towerAOI.entity.*;
import com.whk.towerAOI.script.ITowerScript;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import script.annotation.Script;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Script
@Slf4j
public class TowerScript implements ITowerScript {

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
    public void addWatcher(Tower tower, IMapObject obj) {
        if (Objects.isNull(obj)) return;
        tower.getObjectMap().put(obj.getId(), obj);
        tower.getWatchers().put(obj.getId(), obj);
    }

    @Override
    public void removeWatcher(TowerAOI towerAOI, IMapObject obj) {
        if (Objects.isNull(obj)) return;
        removeWatcher(getTower(towerAOI, obj.getPoint()), obj);
    }

    @Override
    public void removeWatcher(Tower tower, IMapObject obj) {
        if (Objects.isNull(obj)) return;
        tower.getObjectMap().remove(obj.getId(), obj);
        tower.getWatchers().remove(obj.getId());
    }

    @Override
    public void initTowerAOI(TowerAOI towerAOI, Topography topography) {
        int maxTowerX = Math.ceilDiv(towerAOI.getMapWidth(), towerAOI.getTowerXSize());
        int maxTowerY = Math.ceilDiv(towerAOI.getMapHeight(), towerAOI.getTowerYSize());

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
        towerAOI.setTowers(towers);

        for (Point[] points : topography.getAllPoint()) {
            for (Point point : points) {
                getTower(towerAOI, point);
            }
        }
    }


    @Override
    public Set<Tower> getNearTower(TowerAOI towerAOI, Point point, Topography topography, int halfWidth, int halfHeight) {
        Set<Tower> towers = new HashSet<>();
        int towerXSize = towerAOI.getTowerXSize();
        int towerYSize = towerAOI.getTowerYSize();

        // 计算视野矩形在tower坐标系下的索引范围
        int px = (int) point.getX();
        int pz = (int) point.getZ();
        int startTowerX = Math.floorDiv(Math.max(px - halfWidth, 0), towerXSize);
        int endTowerX = Math.floorDiv(Math.min(px + halfWidth, topography.getWidth() - 1), towerXSize);
        int startTowerZ = Math.floorDiv(Math.max(pz - halfHeight, 0), towerYSize);
        int endTowerZ = Math.floorDiv(Math.min(pz + halfHeight, topography.getHeight() - 1), towerYSize);

        // 边界保护
        int maxTx = towerAOI.getMaxTowerX() - 1;
        int maxTz = towerAOI.getMaxTowerY() - 1;
        startTowerX = Math.max(startTowerX, 0);
        startTowerZ = Math.max(startTowerZ, 0);
        endTowerX = Math.min(endTowerX, maxTx);
        endTowerZ = Math.min(endTowerZ, maxTz);

        // 遍历矩形区域内所有 Tower
        Tower[][] allTowers = towerAOI.getTowers();
        for (int tx = startTowerX; tx <= endTowerX; tx++) {
            for (int tz = startTowerZ; tz <= endTowerZ; tz++) {
                Tower tower = allTowers[tx][tz];
                if (tower != null) {
                    towers.add(tower);
                }
            }
        }
        return towers;
    }

    @Override
    public Tower getTower(TowerAOI towerAOI, Point point) {
        return getTower(towerAOI, point.getX(), point.getZ());
    }

    @Override
    public void moveToNextPoint(IMapObject obj, Point nextPoint) {
        SceneInterface scene = obj.getMovement().getScene();
        if (Objects.isNull(nextPoint) || nextPoint.isBlock() || nextPoint.equals(obj.getPoint())) return;
        if (!isNearPoint(obj.getPoint(), nextPoint)) return;
        // 视野宽和高一半
        int halfWidth = obj.getView().getWidth() / 2;
        int halfHeight = obj.getView().getHeight() / 2;
        Set<Tower> oldTowers = getNearTower(scene.getTowerAOI(), obj.getPoint(), scene.getTopography(), halfWidth, halfHeight);
        Set<Tower> newTowers = getNearTower(scene.getTowerAOI(), nextPoint, scene.getTopography(), halfWidth, halfHeight);
        Set<Tower> commonTowers = new HashSet<>(oldTowers);
        commonTowers.retainAll(newTowers);

        oldTowers.removeAll(commonTowers);
        for (Tower oldTower : oldTowers) {
            removeWatcher(oldTower, obj);
            viewRemove(oldTower, obj);
        }

        for (Tower commonTower : commonTowers) {
            viewUpdate(commonTower, obj);
        }

        newTowers.removeAll(commonTowers);
        for (Tower newTower : newTowers) {
            addWatcher(newTower, obj);
            viewAddNew(newTower, obj);
        }
        obj.setPoint(nextPoint);
    }

    private Tower getTower(TowerAOI towerAOI, float x, float z) {
        int towerX = Math.floorDiv((int) x, towerAOI.getTowerXSize());
        int towerY = Math.floorDiv((int) z, towerAOI.getTowerYSize());
        if (towerX < 0 || towerX >= towerAOI.getMaxTowerX()
            || towerY < 0 || towerY >= towerAOI.getMaxTowerY()) {
            log.error("获取灯塔失败，地图id：{}，x={}，z={}", towerAOI.getSceneId(), x, z);
            return null;
        }
        return towerAOI.getTowers()[towerX][towerY];
    }

    public void viewUpdate(Tower tower, IMapObject obj) {
        SceneProto.ResPlayerMove resPlayerMove = SceneProto.ResPlayerMove.newBuilder()
                .setX(obj.getPoint().getX())
                .setZ(obj.getPoint().getZ())
                .setY(obj.getPoint().getY())
                .setPlayerId(obj.getId())
                .build();
        tower.getWatchers().forEach((id, _) -> MessageUtil.getInstance().sendMessage(resPlayerMove, id));
    }

    public void viewRemove(Tower tower, IMapObject obj) {
        SceneProto.ResRemoveView resRemoveView = SceneProto.ResRemoveView.newBuilder()
                .setPlayerId(obj.getId())
                .build();
        tower.getWatchers().forEach((id, _) -> MessageUtil.getInstance().sendMessage(resRemoveView, id));
    }

    public void viewAddNew(Tower tower, IMapObject obj) {
        SceneProto.ResPlayerMove resPlayerMove = SceneProto.ResPlayerMove.newBuilder()
                .setX(obj.getPoint().getX())
                .setZ(obj.getPoint().getZ())
                .setY(obj.getPoint().getY())
                .setPlayerId(obj.getId())
                .build();
        PlayerInfoProto.PlayerSimpleInfo resSimpleInfo = PlayerInfoProto.PlayerSimpleInfo.newBuilder()
                .setPlayerId(obj.getId()).setName(obj.getName()).setLevel(obj.getLevel())
                .setExp(obj.getExp()).setCareer(obj.getCareer()).setSex(obj.getSex())
                .setOnline(true).setCreateTime(obj.getCreateTime()).setServerId(obj.getServerId())
                .setMapId(obj.getMovement().getScene().getMapDef().getId()).build();
        SceneProto.ResEnterView resEnterView = SceneProto.ResEnterView.newBuilder()
                .setMove(resPlayerMove)
                .setSimpleInfo(resSimpleInfo)
                .build();
        tower.getWatchers().forEach((id, _) -> MessageUtil.getInstance().sendMessage(resEnterView, id));
    }

    public boolean isNearPoint(Point origin, Point targetPoint) {
        if (origin.equals(targetPoint)) return false;
        return origin.getX() - targetPoint.getX() <= 1 && origin.getX() - targetPoint.getX() >= -1 &&
                origin.getY() - targetPoint.getY() <= 1 && origin.getY() - targetPoint.getY() >= -1;

    }
}
