package script;

import com.whk.classScan.OutJarScanner;
import com.whk.classScan.ScannerClassException;
import com.whk.classScan.ScannerClassUtil;
import script.annotation.Script;
import script.scriptInterface.IScript;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class ScriptEngine {

    private final Logger logger = Logger.getLogger(ScriptEngine.class.getName());

    private final Map<String, IScript> singleScript = new HashMap<>();

    private final Map<String, List<IScript>> multiScript = new HashMap<>();


    public <T extends Annotation> void reload(Class<T> clazz, String classPath, String artifactId, String scriptArtifactId) {
        singleScript.clear();
        multiScript.clear();
        loadByAnnotation(clazz, classPath, artifactId, scriptArtifactId);
    }

    public void reload(String jarPath) {
        singleScript.clear();
        multiScript.clear();
        loadOutJar(jarPath, Script.class);
    }

    /**
     * 通过注解筛选脚本
     *
     * @param annotation 注解类的类对象
     * @param classPath  脚本路径 例如："com.whk.script.scriptImpl"
     * @param moduleName 模块名
     * @param replace 替换上的模块名
     */
    public <T extends Annotation> void loadByAnnotation(Class<T> annotation, String classPath, String moduleName, String replace) {
        if (Objects.isNull(classPath) || classPath.isEmpty()) {
            classPath = ScriptClassLoader.class.getPackageName();
        }
        assert Objects.nonNull(annotation);
        List<Class<?>> list;
        try {
            list = ScannerClassUtil.INSTANCE
                    .scanClassFile(classPath, aClass -> aClass.isAnnotationPresent(annotation), moduleName, replace);
            putClassProcess(list);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 ScannerClassException e) {
            logger.severe("读取路径错误%s".formatted(e.getMessage()));
        }
    }

    /**
     * 加载外部jar
     * @param jarPath jar路径
     * @param annotation 注解
     */
    public void loadOutJar(String jarPath, Class<? extends Annotation> annotation) {
        OutJarScanner scanner = new OutJarScanner();
        File file = new File(jarPath);
        URI uri = file.toURI();
        List<Class<?>> classes;
        try {
            classes = scanner.search(jarPath, new ScriptClassLoader(new URL[]{uri.toURL()}), aClass -> aClass.isAnnotationPresent(annotation));
            putClassProcess(classes);
        } catch (ScannerClassException | MalformedURLException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            logger.severe("读取路径错误%s".formatted(e.getMessage()));
        }
    }

    private void putClassProcess(List<Class<?>> list) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Class<?> tClass : list) {
            putClass(tClass);
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

    public <T extends IScript> T getScript(Class<T> key) {
        return (T) singleScript.get(key.getName());
    }

    public <T extends IScript> List<T> getMultiScript(Class<T> key) {
        return (List<T>) multiScript.get(key.getName());
    }
}
