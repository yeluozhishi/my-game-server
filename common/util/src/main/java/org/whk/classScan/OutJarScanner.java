package org.whk.classScan;


import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class OutJarScanner implements IClassScan{

    @Override
    public List<Class<?>> search(String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) throws ScannerClassException {
        LinkedList<Class<?>> classes = new LinkedList<>();//所有的Class对象
        JarFile jarFile = null;
        File file;
        try {
            file = new File(packageName);
            jarFile = new JarFile(file);
            Enumeration<JarEntry> es = jarFile.entries();
            while (es.hasMoreElements()) {
                JarEntry jarEntry = es.nextElement();
                String name = jarEntry.getName();
                if (name.endsWith(".class")) {//只解析了.class文件，没有解析里面的jar包
                    Class<?> clazz = classLoader.loadClass(name.replace("/", ".").substring(0, name.length() - 6));//自己定义的loader路径可以找到
                    if (predicate == null || predicate.test(clazz)) {
                        classes.add(clazz);
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new ScannerClassException("读取外部jar包的class出现异常",e);
        } finally {
            if (Objects.nonNull(jarFile)) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                }
            }
        }
        return classes;
    }
}
