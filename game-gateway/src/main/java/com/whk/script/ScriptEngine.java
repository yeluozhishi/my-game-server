package com.whk.script;


import com.whk.script.annotation.Script;
import com.whk.script.scriptInterface.IScript;
import org.whk.ScannerClassUtil;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;

public class ScriptEngine {

    Logger logger = Logger.getLogger(ScriptEngine.class.getName());

    private final Map<String, IScript> singleScript = new HashMap<>();

    private final Map<String, List<IScript>> multiScript = new HashMap<>();


    public <T extends Annotation> void reload(Class<T> clazz, String classPath){
        singleScript.clear();
        multiScript.clear();
        loadByAnnotation(clazz, classPath);
    }

    public void reload(String jarPath){
        singleScript.clear();
        multiScript.clear();
        loadByJar(jarPath);
    }

    /**
     * 通过注解筛选脚本
     *
     * @param clazz     注解类的类对象
     * @param classPath 脚本路径 例如："com.whk.script.scriptImpl"
     */
    public <T extends Annotation> void loadByAnnotation(Class<T> clazz, String classPath) {
        if (Objects.isNull(classPath) || classPath.isEmpty()) {
            classPath = ScriptClassLoader.class.getPackageName();
        }
        assert Objects.nonNull(clazz);
        List<String> classFullNames = ScannerClassUtil.INSTANCE.scannerClass(classPath);
        var list = new LinkedList<Class<?>>();
        var classLoader = Thread.currentThread().getContextClassLoader();;
//        var classLoader = new ScriptClassLoader(((URLClassLoader)ScriptClassLoader.class.getClassLoader()).getURLs(), classPath);
        for (String classFullName : classFullNames) {
            try {
                Class<?> t =  Class.forName(classFullName, false, classLoader);
                if (!t.isAnnotationPresent(clazz)) continue;
                list.add(t);
            } catch (ClassNotFoundException _) {
            }
        }

        putClassProcess(list);
    }


    public void loadByJar(String jarPath) {
        LinkedList<Class<?>> classes = new LinkedList<>();//所有的Class对象
        JarFile jarFile = null;
        ClassLoader loader;
        try {
            File file = new File(jarPath);
            jarFile = new JarFile(file);
            URI uri = file.toURI();
            loader = new ScriptClassLoader(new URL[]{uri.toURL()});//自己定义的classLoader类，把外部路径也加到load路径里，使系统去该路经load对象
            Enumeration<JarEntry> es = jarFile.entries();
            while (es.hasMoreElements()) {
                JarEntry jarEntry = es.nextElement();
                String name = jarEntry.getName();
                if(name.endsWith(".class")){//只解析了.class文件，没有解析里面的jar包
                    //默认去系统已经定义的路径查找对象，针对外部jar包不能用
                    //Class<?> c = Thread.currentThread().getContextClassLoader().loadClass(name.replace("/", ".").substring(0,name.length() - 6));
                    Class<?> c = loader.loadClass(name.replace("/", ".").substring(0,name.length() - 6));//自己定义的loader路径可以找到
                    classes.add(c);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            logger.severe("读取jar包错误%s".formatted(e.getMessage()));
        } finally {
            if (Objects.nonNull(jarFile)) {
                try {
                    jarFile.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        LinkedList<Class<?>> c = new LinkedList<>();
        for (Class<?> aClass : classes) {
            if (aClass.isAnnotationPresent(Script.class)){
                c.add(aClass);
            }
        }
        putClassProcess(c);
    }

    private void putClassProcess(LinkedList<Class<?>> list) {

        try {
            for (Class<?> tClass : list) {
                if (tClass.isAnnotation()) continue;
                putClass(tClass);
            }
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            logger.severe("类型错误" + e.getMessage());
        }
        removeMultiObjectFromSingleScript();
    }

    private void putClass(Class<?> tClass) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var key = tClass.getInterfaces()[0].getName();
        var instance = tClass.getConstructors()[0].newInstance();

        if (multiScript.containsKey(key)) {
            var list = multiScript.get(key);
            list.add((IScript) instance);
            multiScript.put(key, list);
            return;
        }

        if (singleScript.containsKey(key)) {
            var list = multiScript.getOrDefault(key, new LinkedList<>());
            list.add((IScript) instance);
            list.add(singleScript.remove(key));
            multiScript.put(key, list);
            return;
        }
        singleScript.put(key, (IScript) instance);
    }

    private void removeMultiObjectFromSingleScript() {
        for (String s : multiScript.keySet()) {
            singleScript.remove(s);
        }
    }

    public <T extends IScript> T getScript(Class<T> key){
        return (T) singleScript.get(key.getName());
    }

    public <T extends IScript> List<T> getMultiScript(Class<T> key){
        return (List<T>) multiScript.get(key.getName());
    }
}
