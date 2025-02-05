package script;

import lombok.Getter;
import lombok.Setter;
import script.annotation.Script;
import script.scriptInterface.IScript;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

@Getter
@Setter
public class ScriptEngine {

    private final Logger logger = Logger.getLogger(ScriptEngine.class.getName());

    private Map<String, IScript> singleScript = new HashMap<>();

    private Map<String, List<IScript>> multiScript = new HashMap<>();

    private boolean dev;

    private String[] scriptJarFile;

    public ScriptEngine(boolean dev, String[] scriptPath) {
        this.dev = dev;
        this.scriptJarFile = scriptPath;
    }

    public void reload() throws IOException, ScannerClassException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (dev) {
            loadClass(Script.class, "com.whk.script");
        } else {
            loadOutJar(scriptJarFile, Script.class);
        }
        logger.info("脚本加载完成。");
    }

    /**
     * 通过注解筛选脚本
     *
     * @param annotation  注解类的类对象
     * @param packageName 包名
     */
    public void loadClass(Class<? extends Annotation> annotation, String packageName) throws IOException, ScannerClassException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (Objects.isNull(packageName) || packageName.isEmpty()) {
            logger.severe("脚本所在的相对路径。");
        }
        assert Objects.nonNull(annotation);
        List<Class<?>> list;
        var classLoader = new ScriptClassLoader(new URL[]{});
        FileScanner scanner = new FileScanner();
        list = scanner.search(packageName, classLoader, aClass -> aClass.isAnnotationPresent(annotation));
        putClassProcess(list);
    }


    /**
     * 加载外部jar
     *
     * @param jarPath    jar路径
     * @param annotation 注解
     */
    public void loadOutJar(String[] jarPath, Class<? extends Annotation> annotation) throws MalformedURLException, ScannerClassException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class<?>> classes = new LinkedList<>();
        for (String path : jarPath) {
            File file = new File(path);
            URI uri = file.toURI();
            OutJarScanner outJarScanner = new OutJarScanner();
            classes.addAll(outJarScanner.search(path, new ScriptClassLoader(new URL[]{uri.toURL()}), aClass -> aClass.isAnnotationPresent(annotation)));
        }
        putClassProcess(classes);
    }

    private void putClassProcess(List<Class<?>> list) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (list.isEmpty()) return;
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

    public <T extends IScript> T getScript(Class<? extends IScript> key) {
        return (T) singleScript.get(key.getName());
    }

    public <T extends IScript> List<T> getMultiScript(Class<? extends IScript> key) {
        return (List<T>) multiScript.get(key.getName());
    }
}
