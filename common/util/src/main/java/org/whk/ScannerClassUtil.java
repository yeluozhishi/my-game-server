package org.whk;

import java.io.File;
import java.net.URL;
import java.util.*;

public enum ScannerClassUtil {

    INSTANCE;

    /**
     * 扫描类文件
     *
     * @param packageName 包路径
     * @return 类全限定名列表
     */
    public List<String> scannerClass(String packageName) {
        var packageFile = creatFile(packageName);
        List<String> classFullNames = new ArrayList<>();
        Queue<File> files = new LinkedList<>();
        files.add(packageFile);

        while (!files.isEmpty()) {
            var file = files.poll();
            //如果是一个文件夹，加入到文件队列中
            if (file.isDirectory()) {
                files.addAll(List.of(Objects.requireNonNull(file.listFiles())));
            } else {
                var path = file.getPath();
                path = path.substring(packageFile.getPath().length(), path.length() - 6).replaceAll("\\\\", ".");
                classFullNames.add(packageName + path);
            }
        }


        return classFullNames;
    }

    private URL url(String packageName) {
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        assert url != null;
        return url;
    }

    private File creatFile(String packageName) {
        return new File(url(packageName).getFile());
    }
}
