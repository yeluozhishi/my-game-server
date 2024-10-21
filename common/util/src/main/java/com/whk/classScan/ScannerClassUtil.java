package com.whk.classScan;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

public enum ScannerClassUtil {

    INSTANCE;

    /**
     * 扫描类文件
     *
     * @param packageName 包路径
     * @param predicate   筛选
     * @return 类对象
     */
    public List<Class<?>> scanClassJar(String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) throws ScannerClassException, IOException, ClassNotFoundException {
        OutJarScanner outJarScanner = new OutJarScanner();
        return outJarScanner.search(packageName, classLoader, predicate);
    }

}
