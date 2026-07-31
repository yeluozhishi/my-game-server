package com.whk;

import lombok.Getter;
import lombok.Setter;
import org.recast4j.detour.*;

import java.util.Arrays;
import java.util.List;

/**
 * NavMesh 查询服务，封装坐标验证和寻路功能
 */
@Getter
@Setter
public class NavMeshService {

    private final NavMeshQuery query;
    private final DefaultQueryFilter filter;

    public NavMeshService(NavMesh navMesh) {
        this.query = new NavMeshQuery(navMesh);
        // 显式设置 includeFlags=0x01，匹配 NavMesh 构建时的多边形 flags
        float[] areaCost = new float[NavMesh.DT_MAX_AREAS];
        Arrays.fill(areaCost, 1.0f);
        this.filter = new DefaultQueryFilter(0x01, 0, areaCost);
    }

    /**
     * 验证是否有这个坐标点（该坐标是否在 NavMesh 上可达）
     */
    public boolean hasPoint(float x, float y, float z) {
        float[] point = {x, y, z};
        float[] extents = {5000f, 5000f, 5000f};
        Result<FindNearestPolyResult> result = query.findNearestPoly(point, extents, filter);
        if (!result.succeeded()) {
            return false;
        }
        long ref = result.result.getNearestRef();
        if (ref == 0) {
            return false;
        }
        float[] nearest = result.result.getNearestPos();
        float dist = distance(point, nearest);
        // 距离阈值：最近点偏离过大则认为不在 NavMesh 上
        return dist < 1.0f;
    }

    /**
     * 验证坐标点在 NavMesh 上的可达性
     */
    public void verifyPoint(float x, float y, float z) {
        float[] point = {x, y, z};
        float[] extents = {5000f, 5000f, 5000f};

        Result<FindNearestPolyResult> result = query.findNearestPoly(point, extents, filter);

        System.out.println("\n  查询点: (" + fmt(x) + ", " + fmt(y) + ", " + fmt(z) + ")");
        if (result.succeeded()) {
            long ref = result.result.getNearestRef();
            float[] nearest = result.result.getNearestPos();
            float dist = distance(point, nearest);
            System.out.println("  [可达] polyRef=" + Long.toHexString(ref) + " (raw=" + ref + ")");
            System.out.println("         最近点: (" + fmt(nearest[0]) + ", " + fmt(nearest[1]) + ", " + fmt(nearest[2]) + ")");
            System.out.println("         偏离: " + fmt(dist));
        } else {
            System.out.println("  [不可达] status=" + result.status);
        }
    }

    /**
     * 两点间寻路并打印多边形序列
     */
    public void findPathAndPrint(float[] from, float[] to) {
        float[] extents = {5000f, 5000f, 5000f};

        Result<FindNearestPolyResult> startR = query.findNearestPoly(from, extents, filter);
        Result<FindNearestPolyResult> endR = query.findNearestPoly(to, extents, filter);

        System.out.println("  起点: (" + fmt(from[0]) + ", " + fmt(from[1]) + ", " + fmt(from[2]) + ")");
        System.out.println("  终点: (" + fmt(to[0]) + ", " + fmt(to[1]) + ", " + fmt(to[2]) + ")");

        if (!startR.succeeded()) {
            System.out.println("  [失败] 起点不在 NavMesh 上: " + startR.status);
            return;
        }
        if (!endR.succeeded()) {
            System.out.println("  [失败] 终点不在 NavMesh 上: " + endR.status);
            return;
        }

        long startRef = startR.result.getNearestRef();
        long endRef = endR.result.getNearestRef();
        float[] startPos = startR.result.getNearestPos();
        float[] endPos = endR.result.getNearestPos();

        if (startRef == 0) {
            System.out.println("  [失败] startRef=0 (无效多边形引用)");
            return;
        }
        if (endRef == 0) {
            System.out.println("  [失败] endRef=0 (无效多边形引用)");
            return;
        }

        Result<List<Long>> pathResult = query.findPath(
                startRef, endRef,
                startPos,
                endPos,
                filter
        );

        if (pathResult.succeeded()) {
            List<Long> path = pathResult.result;
            System.out.println("  [成功] 经过 " + path.size() + " 个多边形:");
            for (int i = 0; i < path.size(); i++) {
                System.out.println("    [" + i + "] polyRef=" + Long.toHexString(path.get(i)));
            }
        } else {
            System.out.println("  [失败] 不可到达: " + pathResult.status);
        }
    }

    private float distance(float[] a, float[] b) {
        float dx = a[0] - b[0], dy = a[1] - b[1], dz = a[2] - b[2];
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private String fmt(float v) {
        return String.format("%.2f", v);
    }
}
