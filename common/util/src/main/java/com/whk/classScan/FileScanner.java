package com.whk.classScan;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Logger;

@Getter
@Setter
public class FileScanner implements IClassScan {

    Logger logger = Logger.getLogger(FileScanner.class.getName());

    private String defaultPath = Objects.requireNonNull(FileScanner.class.getResource("/")).getPath();

    /**
     * 扫描类文件
     *
     * @param packageName 包路径
     * @return 类全限定名列表
     */
    protected List<Class<?>> scannerClass(String searchPath, String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) throws ScannerClassException {
        File packageFile = new File(searchPath);
        List<Class<?>> classList = new ArrayList<>();
        Queue<File> files = new LinkedList<>();
        files.add(packageFile);

        while (!files.isEmpty()) {
            var file = files.poll();
            //如果是一个文件夹，加入到文件队列中
            if (file.isDirectory()) {
                files.addAll(List.of(Objects.requireNonNull(file.listFiles())));
            } else {
                if (file.getName().endsWith(CLASS_SUFFIX)) {
                    //如果是class文件我们就放入我们的集合中。
                    try {
                        var path = file.getPath();
                        path = path.replace(File.separator, ".").substring(packageFile.getPath().length(), path.length() - 6);
                        Class<?> clazz = classLoader.loadClass(packageName + path);
                        if (predicate == null || predicate.test(clazz)) {
                            classList.add(clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new ScannerClassException("读取本jar包的指定路径下的class出现异常", e);
                    }
                }
            }
        }
        return classList;
    }

    @Override
    public List<Class<?>> search(String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) throws ScannerClassException {
        //先把包名转换为路径,首先得到项目的classpath
        String classpath = defaultPath;
        //然后把我们的包名basPack转换为路径名
        String basePackPath = packageName.replace(".", "/");
        String searchPath = classpath + basePackPath;
        return scannerClass(searchPath, packageName, classLoader, predicate);
    }
}
