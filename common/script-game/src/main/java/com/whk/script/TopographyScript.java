package com.whk.script;

import com.google.protobuf.InvalidProtocolBufferException;
import com.whk.entity.MapDef;
import com.whk.protobuf.message.MapEditorProto;
import com.whk.towerAOI.entity.Point;
import com.whk.towerAOI.entity.Topography;
import com.whk.towerAOI.script.ITopographyScript;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class TopographyScript implements ITopographyScript {

    private final Logger logger = Logger.getLogger(TowerScript.class.getName());

    @Override
    public void initTopography(Topography topography, MapDef mapDef) {
        String filePath = "config/map/" + mapDef.getData() + "/map.byte";
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        byte[] data;
        try {
            data = Files.readAllBytes(classPathResource.getFile().getAbsoluteFile().toPath());
        } catch (IOException e) {
            logger.severe("地图地形信息初始化失败,请检查地形文件[%s mapId: %d]".formatted(filePath, mapDef.getId()));
            throw new RuntimeException(e);
        }

        topography.setWidth(mapDef.getWidth());
        topography.setHeight(mapDef.getHeight());
        //初始化格子
        initGrid(topography);
        //格子属性解析
        initGridsAttribute(mapDef.getId(), data, topography);
        //创建八叉树
        initEightTree(topography);
    }

    private void initGrid(Topography topography) {
        Point[][] pointArray = new Point[topography.getWidth()][topography.getHeight()];
        for (int x = 0; x < topography.getWidth(); x++) {
            for (int y = 0; y < topography.getHeight(); y++) {
                Point point = new Point(x, y);
                pointArray[x][y] = point;
            }
        }
        topography.setAllPoint(pointArray);
    }

    private void initGridsAttribute(int mapId, byte[] data, Topography topography) {
        MapEditorProto.MapInfoList list;
        try {
            list = MapEditorProto.MapInfoList.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            logger.severe("地图信息proto解析失败[mapId: %d]".formatted(mapId));
            throw new RuntimeException(e);
        }

        //读取点的数量
        int nullCount = 0;
        int blockCount = 0;
        int normalCount = 0;
        List<MapEditorProto.Info> gridList = list.getGridList();
        for (MapEditorProto.Info gridInfo : gridList) {
            int x = gridInfo.getCoord() >>> 16;
            int y = gridInfo.getCoord() & 0xFFFF;
            if (x >= topography.getAllPoint().length || y >= topography.getAllPoint()[x].length) {
                logger.severe("地图[%d]地形信息proto解析失败 x y 信息错误".formatted(mapId));
                return;
            }
            Point point = topography.getAllPoint()[x][y];
            if (point == null) {
                logger.severe("地图[%d]地形信息proto解析失败 x y 信息错误".formatted(mapId));
                return;
            }
            int type = gridInfo.getType();
            switch (type) {
                case MapEditorProto.CellType.Normal_VALUE:
                    point.setNormal(true);
                    topography.getWalkPoint().add(point);
                    normalCount++;
                    break;
                case MapEditorProto.CellType.Null_VALUE:
                    topography.getAllPoint()[x][y] = null;
                    nullCount++;
                    break;
                case MapEditorProto.CellType.Resistance_VALUE:
                    point.setNormal(false);
                    point.setBlock(true);
                    blockCount++;
                    break;
                case MapEditorProto.CellType.Safe_VALUE:
                    point.setNormal(true);
                    normalCount++;
                    point.setSafe(true);
                    topography.getWalkPoint().add(point);
                    break;
                case MapEditorProto.CellType.Born_VALUE:
                    point.setNormal(true);
                    normalCount++;
                    point.setBorn(true);
                    topography.getBornPoint().add(point);
                    topography.getWalkPoint().add(point);
                    break;
                case MapEditorProto.CellType.Born_Safe_VALUE:
                    point.setNormal(true);
                    normalCount++;
                    point.setBorn(true);
                    point.setSafe(true);
                    topography.getBornPoint().add(point);
                    topography.getWalkPoint().add(point);
                    break;
                default:
                    logger.severe("地图[%d]地形信息proto解析失败 未实现的类型".formatted(mapId));
                    return;
            }
        }
        int total = nullCount + blockCount + normalCount;
        logger.info("地图=%d null=%d block=%d normal=%d total=%d area=%d ".formatted(mapId, nullCount, blockCount, normalCount, total, topography.getWidth() * topography.getHeight()));
    }

    /**
     * 1 1 1 1
     * 1 0 1 1
     * 1 1 1 1
     * <p>
     * 周围点定义
     * 0 1 2
     * 3 _ 4
     * 5 6 7
     *
     * @param topography 地图数据
     */
    private void initEightTree(Topography topography) {
        for (int x = 0; x < topography.getWidth(); x++) {
            for (int y = 0; y < topography.getHeight(); y++) {
                Point point = topography.getAllPoint()[x][y];
                if (Objects.isNull(point)) continue;
                if (Objects.isNull(point.getNears())) {
                    point.setNears(new Point[8]);
                }
                // 右点
                setNearPoints(point, 4, topography, x + 1, y, 3);
                // 右下点
                setNearPoints(point, 7, topography, x + 1, y + 1, 0);
                // 下点
                setNearPoints(point, 6, topography, x, y + 1, 1);
                // 左下点
                setNearPoints(point, 5, topography, x - 1, y + 1, 2);
            }
        }
    }

    private void setNearPoints(Point point, int nearPosition, Topography topography, int x, int y, int nextNearPosition) {
        if (x < 0 || y < 0 || x >= topography.getWidth() || y >= topography.getHeight()) return;
        Point next = topography.getAllPoint()[x][y];
        if (Objects.nonNull(next)) {
            point.getNears()[nearPosition] = next;
            if (Objects.isNull(next.getNears())) {
                next.setNears(new Point[8]);
            }
            next.getNears()[nextNearPosition] = point;
        }
    }

}
