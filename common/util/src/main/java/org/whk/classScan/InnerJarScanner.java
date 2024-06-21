package org.whk.classScan;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class InnerJarScanner implements IClassScan{
    @Override
    public List<Class<?>> search(String packageName, ClassLoader classLoader, Predicate<Class<?>> predicate) throws ScannerClassException {

        List<Class<?>> classes = new LinkedList<>();

        try {
            //通过当前线程得到类加载器从而得到URL的枚举
            Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".", "/"));
            while (urlEnumeration.hasMoreElements()) {
                //得到的结果大概是：jar:file:/C:/Users/ibm/.m2/repository/junit/junit/4.12/junit-4.12.jar!/org/junit
                URL url = urlEnumeration.nextElement();
                //大概是jar
                String protocol = url.getProtocol();
                if ("jar".equalsIgnoreCase(protocol)) {
                    //转换为JarURLConnection
                    JarURLConnection connection = (JarURLConnection) url.openConnection();
                    if (connection != null) {
                        JarFile jarFile = connection.getJarFile();
                        if (jarFile != null) {
                            //得到该jar文件下面的类实体
                            Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                            while (jarEntryEnumeration.hasMoreElements()) {
                            /*entry的结果大概是这样：
                                    org/
                                    org/junit/
                                    org/junit/rules/
                                    org/junit/runners/*/
                                JarEntry entry = jarEntryEnumeration.nextElement();
                                String jarEntryName = entry.getName();
                                //这里我们需要过滤不是class文件和不在basePack包名下的类
                                if (jarEntryName.contains(".class") && jarEntryName.replaceAll("/", ".").startsWith(packageName)) {
                                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
                                    Class cls = Class.forName(className);
                                    if(predicate == null || predicate.test(cls)){
                                        classes.add(cls);
                                    }
                                }
                            }
                        }
                    }
                }else if("file".equalsIgnoreCase(protocol)){
                    //从maven子项目中扫描
                    FileScanner fileScanner = new FileScanner();
                    fileScanner.setDefaultPath(url.getPath().replace(packageName.replace(".", "/"),""));
                    classes.addAll(fileScanner.search(packageName,classLoader, predicate));
                }
            }
        }catch (ClassNotFoundException | IOException e){
            throw new ScannerClassException("读取本jar包的class出现异常",e);
        }
        return classes;
    }
}
