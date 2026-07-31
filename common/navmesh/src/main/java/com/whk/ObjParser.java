package com.whk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Wavefront OBJ 文件解析器，提取顶点坐标和面索引
 */
public class ObjParser {

    public static class Result {
        public final float[] vertices;
        public final int[] faces;
        public Result(float[] vertices, int[] faces) {
            this.vertices = vertices;
            this.faces = faces;
        }
    }

    /**
     * 解析 OBJ 文件，返回顶点数组和面索引数组（0-based）
     */
    public static Result parse(String filePath) throws IOException {
        float[][] verts = new float[1024][3];
        int vertCount = 0;
        int[][] faces = new int[1024][3];
        int faceCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\s+");
                if (parts[0].equals("v") && parts.length >= 4) {
                    if (vertCount >= verts.length) {
                        verts = resizeVerts(verts, verts.length * 2);
                    }
                    verts[vertCount][0] = Float.parseFloat(parts[1]);
                    verts[vertCount][1] = Float.parseFloat(parts[2]);
                    verts[vertCount][2] = Float.parseFloat(parts[3]);
                    vertCount++;
                } else if (parts[0].equals("f") && parts.length >= 4) {
                    if (faceCount >= faces.length) {
                        faces = resizeFaces(faces, faces.length * 2);
                    }
                    for (int i = 1; i <= 3; i++) {
                        int idx = Integer.parseInt(parts[i].split("/")[0]);
                        faces[faceCount][i - 1] = idx > 0 ? idx - 1 : idx;
                    }
                    faceCount++;
                }
            }
        }

        // 转换为扁平数组
        float[] flatVerts = new float[vertCount * 3];
        for (int i = 0; i < vertCount; i++) {
            flatVerts[i * 3] = verts[i][0];
            flatVerts[i * 3 + 1] = verts[i][1];
            flatVerts[i * 3 + 2] = verts[i][2];
        }
        int[] flatFaces = new int[faceCount * 3];
        for (int i = 0; i < faceCount; i++) {
            flatFaces[i * 3] = faces[i][0];
            flatFaces[i * 3 + 1] = faces[i][1];
            flatFaces[i * 3 + 2] = faces[i][2];
        }
        return new Result(flatVerts, flatFaces);
    }

    private static float[][] resizeVerts(float[][] src, int newSize) {
        float[][] dst = new float[newSize][3];
        System.arraycopy(src, 0, dst, 0, src.length);
        return dst;
    }

    private static int[][] resizeFaces(int[][] src, int newSize) {
        int[][] dst = new int[newSize][3];
        System.arraycopy(src, 0, dst, 0, src.length);
        return dst;
    }
}
