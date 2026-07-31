package com.whk;

import org.recast4j.detour.*;
import org.recast4j.recast.*;
import org.recast4j.recast.geom.SimpleInputGeomProvider;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;

/**
 * NavMesh 简单测试：解析 OBJ → 构建 NavMesh(内存) → 查询验证
 */
public class Main {

    static final String DATA_DIR = "common/navmesh/src/main/navmeshdata/";
    static final String[] OBJ_FILE = {"CharacterSelect.obj", "Dungeon.obj"};

    public static void main(String[] args) throws Exception {
        buildNavMesh();

        // 3. 坐标查询验证
        System.out.println("\n=== 3. 坐标查询验证 ===");
        NavMeshService service = NavMeshManager.INSTANCE.find(1);

        float[][] testPoints = {
            {10.45f, 0.053f, 12.54f},
            {10.45f, 0.053f, 11.20f},
            {-11.00f, 0.053f, 10.64f},
            {0f, 0f, 0f},
            {-12.00f, 1f, 11.00f},
            {-14.00f, 0.053f, 12.00f},
            {10.00f, 0.06f, 12.00f},
        };
        for (float[] pt : testPoints) {
            service.verifyPoint(pt[0], pt[1], pt[2]);
        }

        // 4. 路径查找演示
        long now = System.currentTimeMillis();
        System.out.println("\n=== 4. 路径查找演示 ===");
        service.findPathAndPrint(
            new float[]{10.45f, 0.053f, 12.54f},
            new float[]{-14.00f, 0.053f, 12.00f}
        );
        service.findPathAndPrint(
            new float[]{10f, 0.06f, -12.00f},
            new float[]{-10f, 0.06f, 12.00f}
        );
        service.findPathAndPrint(
            new float[]{5.0f, 0.05f, 4.20f},
            new float[]{10.5f, 0.05f, 18.20f}
        );

        System.out.println("\n=== 全部完成 === time: " + (System.currentTimeMillis() - now) + "ms");
    }


    public static void buildNavMesh() throws IOException {
        // 1. 解析 OBJ
        System.out.println("=== 1. 解析 OBJ 文件 ===");
        int i = 0;
        for (String fileName : OBJ_FILE) {
            i++;
            Path objPath = Paths.get(DATA_DIR, fileName);
            System.out.println("文件: " + objPath.toAbsolutePath());
            ObjParser.Result objData = ObjParser.parse(objPath.toString());
            float[] vertices = objData.vertices;
            int[] faces = objData.faces;
            System.out.println("顶点数: " + vertices.length / 3 + ", 三角形数: " + faces.length / 3);
            // 打印前几个三角形验证数据
            int triCount = Math.min(3, faces.length / 3);
            for (int t = 0; t < triCount; t++) {
                int v0 = faces[t * 3], v1 = faces[t * 3 + 1], v2 = faces[t * 3 + 2];
                System.out.printf("  Tri %d: [%d](%.2f,%.2f,%.2f) [%d](%.2f,%.2f,%.2f) [%d](%.2f,%.2f,%.2f)%n",
                    t, v0, vertices[v0*3], vertices[v0*3+1], vertices[v0*3+2],
                    v1, vertices[v1*3], vertices[v1*3+1], vertices[v1*3+2],
                    v2, vertices[v2*3], vertices[v2*3+1], vertices[v2*3+2]);
            }

            // 2. 构建 NavMesh（仅内存）
            System.out.println("\n=== 2. 构建 NavMesh " + fileName + " ===");
            SimpleInputGeomProvider geom = new SimpleInputGeomProvider(vertices, faces);
            float[] bmin = geom.getMeshBoundsMin();
            float[] bmax = geom.getMeshBoundsMax();
            System.out.printf("  包围盒: (%.2f, %.2f, %.2f) ~ (%.2f, %.2f, %.2f)%n",
                    bmin[0], bmin[1], bmin[2], bmax[0], bmax[1], bmax[2]);
            System.out.printf("  尺寸: X=%.2f  Y=%.2f  Z=%.2f%n",
                    bmax[0] - bmin[0], bmax[1] - bmin[1], bmax[2] - bmin[2]);
            NavMesh navMesh = buildNavMesh(geom);

            System.out.println("NavMesh 构建完成! 瓦片数: " + navMesh.getMaxTiles());
            NavMeshManager.INSTANCE.addNavMesh(i, new NavMeshService(navMesh));
        }

    }

    private static NavMesh buildNavMesh(SimpleInputGeomProvider geom) {
        // 根据 UE NavMesh 项目设置: CellSize=19.0cm, CellHeight=10.0cm
        // 转换为米: ×0.01
        RecastConfig cfg = new RecastConfig(
                RecastConstants.PartitionType.WATERSHED,  // 与 UE 一致
                0.19f,    // cellSize (UE: 19cm → 0.19m)
                0.10f,    // cellHeight (UE: 10cm → 0.10m)
                1.44f,    // agentHeight (UE: 144cm → 1.44m)
                0.35f,    // agentRadius (UE: 35cm → 0.35m)
                0.35f,    // agentMaxClimb (UE: 35cm → 0.35m)
                45.0f,    // agentMaxSlope (度，UE默认45°)
                0,        // regionMinSize
                0,        // regionMergeSize
                0.12f,    // edgeMaxLen
                0.013f,   // edgeMaxError (UE: MaxSimplificationError)
                6,        // vertsPerPoly
                0.9f,     // detailSampleDist
                1.0f,     // detailSampleMaxError
                new AreaModification(63)
        );

        // 包围盒需要向外扩展一点，避免边界三角形被裁剪
        // 扩展量为 cellSize * 2 + agentRadius，确保边界几何体被正确包含
        float borderSize = cfg.cs * 2.0f + cfg.walkableRadiusWorld;
        float[] bmin = geom.getMeshBoundsMin();
        float[] bmax = geom.getMeshBoundsMax();
        float[] expandedBmin = new float[]{bmin[0] - borderSize, bmin[1] - borderSize, bmin[2] - borderSize};
        float[] expandedBmax = new float[]{bmax[0] + borderSize, bmax[1] + borderSize, bmax[2] + borderSize};

        RecastBuilderConfig builderCfg = new RecastBuilderConfig(cfg, expandedBmin, expandedBmax);
        System.out.println("  Expanded Bounds: (" + expandedBmin[0] + ", " + expandedBmin[1] + ", " + expandedBmin[2]
                + ") ~ (" + expandedBmax[0] + ", " + expandedBmax[1] + ", " + expandedBmax[2] + ")");
        RecastBuilder.RecastBuilderResult result = new RecastBuilder().build(geom, builderCfg);

        // 打印各阶段结果
        System.out.println("  CompactHeightfield spanCount: " + (result.getCompactHeightfield() != null ? result.getCompactHeightfield().spanCount : 0));
        System.out.println("  PolyMesh 顶点: " + result.getMesh().nverts + ", 多边形: " + result.getMesh().npolys);
        if (result.getMeshDetail() != null) {
            System.out.println("  DetailMesh 顶点: " + result.getMeshDetail().nverts
                + ", 三角形: " + result.getMeshDetail().ntris);
        }

        NavMeshDataCreateParams params = new NavMeshDataCreateParams();
        params.verts = result.getMesh().verts;
        params.vertCount = result.getMesh().nverts;
        params.polys = result.getMesh().polys;
        params.polyAreas = result.getMesh().areas;
        int[] flags = new int[result.getMesh().npolys];
        Arrays.fill(flags, 0x01);
        params.polyFlags = flags;
        params.polyCount = result.getMesh().npolys;
        params.nvp = result.getMesh().nvp;
        params.walkableHeight = cfg.walkableHeightWorld;
        params.walkableRadius = cfg.walkableRadiusWorld;
        params.walkableClimb = cfg.walkableClimbWorld;
        params.cs = cfg.cs;
        params.ch = cfg.ch;
        params.bmin = geom.getMeshBoundsMin();
        params.bmax = geom.getMeshBoundsMax();
        params.buildBvTree = true;
        if (result.getMeshDetail() != null) {
            params.detailMeshes = result.getMeshDetail().meshes;
            params.detailVerts = result.getMeshDetail().verts;
            params.detailVertsCount = result.getMeshDetail().nverts;
            params.detailTris = result.getMeshDetail().tris;
            params.detailTriCount = result.getMeshDetail().ntris;
        }
        MeshData meshData = NavMeshBuilder.createNavMeshData(params);

        // 单瓦片模式，orig 和 tile 尺寸需要与 builder 的包围盒一致
        NavMeshParams navParams = new NavMeshParams();
        navParams.orig[0] = expandedBmin[0];
        navParams.orig[1] = expandedBmin[1];
        navParams.orig[2] = expandedBmin[2];
        navParams.tileWidth = (float) Math.ceil(expandedBmax[0] - expandedBmin[0]);
        navParams.tileHeight = (float) Math.ceil(expandedBmax[2] - expandedBmin[2]);
        navParams.maxTiles = 1;
        navParams.maxPolys = 1 << 16;

        NavMesh navMesh = new NavMesh(navParams, params.nvp);
        long addTileResult = navMesh.addTile(meshData, 0, 0);
        System.out.println("  NavMesh 多边形数: " + meshData.header.polyCount
            + ", addTile=" + (addTileResult != 0 ? "OK" : "FAIL"));
        return navMesh;
    }
}
