package script;

import script.scriptInterface.IClassScan;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class FileScanner implements IClassScan {

    Logger logger = Logger.getLogger(FileScanner.class.getName());

    @Override
    public List<Class<?>> search(String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) throws IOException, ScannerClassException {
        Set<Class<?>> allClazz = new LinkedHashSet<>();
        String packageDir = packageName.replace('.', File.separatorChar);
        Enumeration<URL> dirs = classLoader.getResources(packageDir);
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                allClazz.addAll(findClassFromDir(classLoader, packageName, filePath));
            } else if ("jar".equals(protocol)) {
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                allClazz.addAll(findClassFromJar(classLoader, jar, packageDir));
            }
        }
        List<Class<?>> ret = new LinkedList<>();
        for (Class<?> clazz : allClazz) {
            if (predicate.test(clazz)){
                ret.add(clazz);
            }
        }
        return ret;
    }

    private Set<Class<?>> findClassFromDir(ClassLoader classLoader, String packageName, String filePath) throws ScannerClassException {
        File dir = new File(filePath);
        if (dir.exists() && dir.isDirectory()) {
            Set<Class<?>> ret = new LinkedHashSet<>();
            File[] files = dir.listFiles((f) -> f.isDirectory() || f.getName().endsWith(".class"));
            assert files != null;

            for (File file : files) {
                if (file.isDirectory()) {
                    ret.addAll(findClassFromDir(classLoader, "%s.%s".formatted(packageName, file.getName()), file.getAbsolutePath()));
                } else {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    try {
                        Class<?> clazz = Class.forName(packageName + '.' + className, false, classLoader);
                        ret.add(clazz);
                    } catch (ClassNotFoundException e) {
                        throw new ScannerClassException("读取文件夹中的Class文件出错%s".formatted(className), e);
                    }
                }
            }
            return ret;
        } else {
            return Collections.emptySet();
        }
    }

    private Set<Class<?>> findClassFromJar(ClassLoader classLoader, JarFile jar, String packageDir) {
        Set<Class<?>> ret = new LinkedHashSet<>();
        Enumeration<JarEntry> entries = jar.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (!entry.isDirectory()) {
                String name = entry.getName();
                if (name.startsWith(packageDir) && name.endsWith(".class")) {
                    name = name.replaceAll("/", ".");
                    name = name.substring(0, name.length() - 6);

                    try {
                        Class<?> clazz = Class.forName(name, false, classLoader);
                        ret.add(clazz);
                    } catch (Throwable var8) {
                        logger.severe("读取Jar中的Class文件出错:%s".formatted(name));
                    }
                }
            }
        }

        return ret;
    }
}
