package script;

import com.whk.classScan.ScannerClassException;
import com.whk.classScan.ScannerClassUtil;
import script.annotation.Script;
import script.scriptInterface.IScript;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class ScriptEngine {

    private final Logger logger = Logger.getLogger(ScriptEngine.class.getName());

    private Map<String, IScript> singleScript = new HashMap<>();

    private Map<String, List<IScript>> multiScript = new HashMap<>();


    public <T extends Annotation> void reload(Class<T> clazz, String pathInModule) {
        loadByAnnotation(clazz, pathInModule);
    }

    public void reload(String jarPath) {
        loadOutJar(jarPath, Script.class);
    }

    /**
     * 通过注解筛选脚本
     *
     * @param annotation 注解类的类对象
     * @param pathInModule  脚本路径 例如："com.whk.script.scriptImpl"
     */
    public <T extends Annotation> void loadByAnnotation(Class<T> annotation, String pathInModule) {
        if (Objects.isNull(pathInModule) || pathInModule.isEmpty()) {
            logger.severe("填写项目路径下，脚本的所在的相对路径。");
        }
        assert Objects.nonNull(annotation);
        List<Class<?>> list;
        try {
            String projectPath = System.getProperty("user.dir");
            String searchPath = projectPath + pathInModule;
            var classLoader = new ScriptClassLoader(new URL[]{});
            FileScanner scanner = new FileScanner();
            list = scanner.scannerClass(searchPath, classLoader, aClass -> aClass.isAnnotationPresent(annotation));
            putClassProcess(list);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            logger.severe("读取路径错误%s".formatted(e.getMessage()));
        } catch (ScannerClassException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载外部jar
     * @param jarPath jar路径
     * @param annotation 注解
     */
    public void loadOutJar(String jarPath, Class<? extends Annotation> annotation) {
        File file = new File(jarPath);
        URI uri = file.toURI();
        List<Class<?>> classes;
        try {
            classes = ScannerClassUtil.INSTANCE.scanClassJar(jarPath, new ScriptClassLoader(new URL[]{uri.toURL()}), aClass -> aClass.isAnnotationPresent(annotation));
            putClassProcess(classes);
        } catch (ScannerClassException | MalformedURLException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            logger.severe("读取路径错误%s".formatted(e.getMessage()));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void putClassProcess(List<Class<?>> list) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Map<String, List<IScript>> multiScript = new HashMap<>();
        Map<String, IScript> singleScript = new HashMap<>();
        for (Class<?> tClass : list) {
            putClass(tClass, multiScript, singleScript);
        }
        for (String s : multiScript.keySet()) {
            singleScript.remove(s);
        }
        this.multiScript = multiScript;
        this.singleScript = singleScript;
    }

    private void putClass(Class<?> tClass, Map<String, List<IScript>> multiScript, Map<String, IScript> singleScript) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var key = tClass.getInterfaces()[0].getName();
        var instance = tClass.getConstructors()[0].newInstance();

        if (multiScript.containsKey(key)) {
            var list = this.multiScript.get(key);
            list.add((IScript) instance);
            this.multiScript.put(key, list);
            return;
        }

        if (singleScript.containsKey(key)) {
            var list = this.multiScript.getOrDefault(key, new LinkedList<>());
            list.add((IScript) instance);
            list.add(this.singleScript.remove(key));
            this.multiScript.put(key, list);
            return;
        }
        singleScript.put(key, (IScript) instance);
    }

    public <T extends IScript> T getScript(Class<T> key) {
        return (T) singleScript.get(key.getName());
    }

    public <T extends IScript> List<T> getMultiScript(Class<T> key) {
        return (List<T>) multiScript.get(key.getName());
    }
}
