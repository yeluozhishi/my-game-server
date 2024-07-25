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
     * @param predicate 筛选
     * @return 类全限定名列表
     */
    public List<Class<?>> scanClassFile(String packageName, Predicate<Class<?>> predicate) throws ScannerClassException {
        FileScanner fileScanner = new FileScanner();
        return fileScanner.search(packageName, Thread.currentThread().getContextClassLoader(), predicate);
    }


    /**
     * 扫描类文件
     *
     * @param packageName 包路径
     * @param predicate 筛选
     * @return 类全限定名列表
     */
    public List<Class<?>> scanClassFile(String packageName, Predicate<Class<?>> predicate, String moduleName, String replace) throws ScannerClassException {
        ModuleScanner fileScanner = new ModuleScanner();
        fileScanner.setModuleName(moduleName);
        fileScanner.setReplace(replace);
        return fileScanner.search(packageName, Thread.currentThread().getContextClassLoader(), predicate);
    }

}
