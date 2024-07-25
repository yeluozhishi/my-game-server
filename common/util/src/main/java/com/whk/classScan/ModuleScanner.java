package com.whk.classScan;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

@Getter
@Setter
public class ModuleScanner extends FileScanner {

    Logger logger = Logger.getLogger(ModuleScanner.class.getName());

    private String moduleName = "";

    private String replace = "";

    @Override
    public List<Class<?>> search(String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) throws ScannerClassException {
        //先把包名转换为路径,首先得到项目的classpath
        String classpath = getDefaultPath();
        //然后把我们的包名basPack转换为路径名
        classpath = classpath.replace(moduleName, replace);

        String basePackPath = packageName.replace(".", "/");
        String searchPath = classpath + basePackPath;
        return scannerClass(searchPath, packageName, classLoader, predicate);
    }
}
