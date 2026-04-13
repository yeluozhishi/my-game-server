package script;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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

@Getter
@Setter
@Slf4j
public class ScriptEngine {

    private Map<String, IScript> singleScript = new HashMap<>();


    private boolean dev;

    private String scriptJarFile;

    public ScriptEngine(boolean dev, String scriptPath) {
        this.dev = dev;
        this.scriptJarFile = scriptPath;
    }

    public void reload() throws IOException, ScannerClassException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (dev) {
            loadClass(Script.class, "com.whk.script");
        } else {
            loadOutJar(scriptJarFile, Script.class);
        }
        log.info("脚本加载完成。");
    }

    /**
     * 通过注解筛选脚本
     *
     * @param annotation  注解类的类对象
     * @param packageName 包名
     */
    public void loadClass(Class<? extends Annotation> annotation, String packageName) throws IOException, ScannerClassException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (Objects.isNull(packageName) || packageName.isEmpty()) {
            log.error("脚本所在的相对路径。");
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
    public void loadOutJar(String jarPath, Class<? extends Annotation> annotation) throws MalformedURLException, ScannerClassException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File file = new File(jarPath);
        URI uri = file.toURI();
        OutJarScanner outJarScanner = new OutJarScanner();
        List<Class<?>> classes = new LinkedList<>(outJarScanner.search(jarPath, new ScriptClassLoader(new URL[]{uri.toURL()}), aClass -> aClass.isAnnotationPresent(annotation)));
        putClassProcess(classes);
    }

    private void putClassProcess(List<Class<?>> list) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (list.isEmpty()) return;
        Map<String, IScript> singleScript = new HashMap<>();
        for (Class<?> tClass : list) {
            putClass(tClass, singleScript);
        }
        this.singleScript = singleScript;
    }

    private void putClass(Class<?> tClass, Map<String, IScript> singleScript) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var key = tClass.getInterfaces()[0].getName();
        var instance = tClass.getConstructors()[0].newInstance();
        singleScript.put(key, (IScript) instance);
    }

    public <T extends IScript> T getScript(Class<T> key) {
        IScript script = singleScript.get(key.getName());
        return key.cast(script);
    }
}
